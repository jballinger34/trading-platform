package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.OrderCancelledEvent;
import com.mthree.TradingPlatform.events.OrderProcessedEvent;
import com.mthree.TradingPlatform.events.TradeExecutedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TempConsumer {

    @KafkaListener(
            topics = "orders.placed",
            groupId = "trading-platform"
    )
    public void listen(OrderProcessedEvent event){
        System.out.println("Order placed: " + event.toString());
    }

    @KafkaListener(
            topics = "orders.cancelled",
            groupId = "trading-platform"
    )
    public void listen(OrderCancelledEvent event){
        System.out.println("Order cancelled: " + event.toString());
    }

    @KafkaListener(
            topics = "trades.executed",
            groupId = "trading-platform"
    )
    public void listen(TradeExecutedEvent event){
        System.out.println("Trade executed: " + event.toString());
    }
}
