package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.client.PortfolioClient;
import com.mthree.TradingPlatform.client.WalletClient;
import com.mthree.TradingPlatform.domain.model.OrderSide;
import com.mthree.TradingPlatform.requests.CancelOrderRequest;
import com.mthree.TradingPlatform.requests.PlaceOrderRequest;
import com.mthree.TradingPlatform.events.OrderCancelCommand;
import com.mthree.TradingPlatform.events.OrderPlacedEvent;
import com.mthree.TradingPlatform.events.ReserveFundsEvent;
import com.mthree.TradingPlatform.events.ReserveStockEvent;
import com.mthree.TradingPlatform.kafka.EventProducer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {

    //TODO
    // discuss merging the 2 ReserveEvents and OrderPlacedEvent all into one (OrderPlacedEvent),
    // and having consumers decide whether they are going

    private final WalletClient walletClient;
    private final PortfolioClient portfolioClient;
    private final EventProducer eventProducer;

    public OrderService(WalletClient walletClient, PortfolioClient portfolioClient, EventProducer eventProducer) {
        this.walletClient = walletClient;
        this.portfolioClient = portfolioClient;
        this.eventProducer = eventProducer;
    }

    public void placeOrder(UUID userId, PlaceOrderRequest request) {
        if (request.orderSide() == OrderSide.SELL) {
            placeSellOrder(userId, request);
        } else {
            placeBuyOrder(userId, request);
        }
    }
    public void cancelOrder(UUID userId, CancelOrderRequest request){
        eventProducer.publishCancelOrder(new OrderCancelCommand(userId, request.symbol(), request.orderId()));
    }

    private void placeBuyOrder(UUID userId, PlaceOrderRequest request) {
        if (request == null || request.orderSide() != OrderSide.BUY) return;
        //check if they have the funds

        BigDecimal totalPrice = request.price().multiply(BigDecimal.valueOf(request.quantity()));
        BigDecimal funds = walletClient.getFunds(userId);
        if(funds.compareTo(totalPrice) < 0){
            //handle insufficient funds
            return;
        }

        //send kafka event to reserve funds - wallet module consumes this to reserve funds
        // then that order has been placed - orderbook consumes to process
        eventProducer.publishReserveFunds(new ReserveFundsEvent(userId, totalPrice));
        eventProducer.publishOrderPlaced(OrderPlacedEvent.create(request.symbol(), userId, request.quantity(), request.price(), request.orderSide()));
    }

    private void placeSellOrder(UUID userId, PlaceOrderRequest request) {
        if (request == null || request.orderSide() != OrderSide.SELL) return;

        long sellQuantity = request.quantity();
        long portfolioQuantity = portfolioClient.getHoldingQuantity(userId, request.symbol());
        if(portfolioQuantity < sellQuantity){
            return;
        }

        eventProducer.publishReserveStock(new ReserveStockEvent(userId, request.symbol(), sellQuantity));
        eventProducer.publishOrderPlaced(OrderPlacedEvent.create(request.symbol(), userId, request.quantity(), request.price(), request.orderSide()));
    }



}
