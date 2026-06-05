package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.domain.model.*;
import com.mthree.TradingPlatform.events.*;
import com.mthree.TradingPlatform.kafka.EventProducer;
import com.mthree.TradingPlatform.manager.OrderBookManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderBookService {

    private final OrderBookManager orderBookManager;

    private final EventProducer eventProducer;

    public OrderBookService(OrderBookManager orderBookManager, EventProducer eventProducer) {
        this.orderBookManager = orderBookManager;
        this.eventProducer = eventProducer;
    }

    public void processOrder(Order order) {

        List<Trade> trades = orderBookManager.match(order);

        eventProducer.publishOrderProcessed(new OrderProcessedEvent(UUID.randomUUID(), Instant.now(),
                order.getOrderId(), order.getUserId(), order.getSymbol(), order.getSide(), order.getPrice(), order.getQuantity()));

        for(Trade trade  : trades){
            eventProducer.publishTradeExecuted(new TradeExecutedEvent(UUID.randomUUID(), Instant.now(), trade));
        }
    }
    public BigDecimal getHighestBid(String symbol){
        OrderBook book = orderBookManager.getBook(symbol);
        if(book.getBids().isEmpty()){
            return null;
        }
        return book.getBids().firstKey();
    }
    public BigDecimal getLowestAsk(String symbol){
        OrderBook book = orderBookManager.getBook(symbol);
        if(book.getAsks().isEmpty()){
            return null;
        }
        return book.getAsks().firstKey();
    }
    public void cancelOrder(String symbol, UUID orderId){
        Order cancelled = orderBookManager.cancel(symbol,orderId);
        if(cancelled == null) return;

        if(cancelled.getSide() == OrderSide.SELL){
            eventProducer.publishUnreserveStock(new UnreserveStockEvent(cancelled.getUserId(),symbol,cancelled.getRemainingQuantity()));
        } else {
            BigDecimal fundsToUnreserve = cancelled.getPrice().multiply(BigDecimal.valueOf(cancelled.getRemainingQuantity()));
            eventProducer.publicUnreserveFunds(new UnreserveFundsEvent(cancelled.getUserId(), fundsToUnreserve));
        }
    }



}
