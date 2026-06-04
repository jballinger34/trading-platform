package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.domain.model.*;
import com.mthree.TradingPlatform.events.*;
import com.mthree.TradingPlatform.kafka.EventProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderBookService {

    // TODO
    // order book snapshots for persistence
    //  throw exception for book not found

    //in memory, need to add saving snapshots later
    Map<String, OrderBook> books = new HashMap<>();

    private final EventProducer eventProducer;

    public OrderBookService(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public List<Trade> processOrder(String symbol, Order order) {
        OrderBook book = getOrCreateOrderBook(symbol);

        List<Trade> trades = book.match(order);
        eventProducer.publishOrderProcessed(new OrderProcessedEvent(UUID.randomUUID(), Instant.now(),
                order.getOrderId(), order.getUserId(), symbol, order.getSide(), order.getPrice(), order.getQuantity()));

        for(Trade trade  : trades){
            eventProducer.publishTradeExecuted(new TradeExecutedEvent(UUID.randomUUID(), Instant.now(), trade));
        }
        return trades;
    }
    public BigDecimal getHighestBid(String symbol){
        OrderBook book = getOrCreateOrderBook(symbol);
        if(book == null || book.getBids().isEmpty()){
            return null;
        }
        return book.getBids().firstKey();
    }
    public BigDecimal getLowestAsk(String symbol){
        OrderBook book = getOrCreateOrderBook(symbol);
        if(book == null || book.getAsks().isEmpty()){
            return null;
        }
        return book.getAsks().firstKey();
    }
    public void cancelOrder(String symbol, UUID orderId){
        OrderBook book = getOrCreateOrderBook(symbol);
        if(book == null) return;
        Order cancelled = book.cancel(orderId);
        if(cancelled == null) return;

        if(cancelled.getSide() == OrderSide.SELL){
            eventProducer.publishUnreserveStock(
                    new UnreserveStockEvent(cancelled.getUserId(),symbol,cancelled.getRemainingQuantity()));
        } else {
            BigDecimal fundsToUnreserve = cancelled.getPrice().multiply(BigDecimal.valueOf(cancelled.getRemainingQuantity()));
            eventProducer.publicUnreserveFunds(
                    new UnreserveFundsEvent(cancelled.getUserId(), fundsToUnreserve)
            );
        }
        //we no longer publish this here, wait for unreserve stock/funds success event back
        //then consume it, and publish this in a seperate method
        //eventProducer.publishOrderCancelled(new OrderCancelSuccessEvent(UUID.randomUUID(), Instant.now(), orderId, symbol));
    }
    public OrderBook getOrCreateOrderBook(String symbol){
        return books.computeIfAbsent(symbol, OrderBook::new);
    }



}
