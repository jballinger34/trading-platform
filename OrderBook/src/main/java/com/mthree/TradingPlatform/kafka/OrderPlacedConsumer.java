package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.domain.model.Order;
import com.mthree.TradingPlatform.events.OrderPlacedEvent;
import com.mthree.TradingPlatform.service.OrderBookService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedConsumer {
    
    private final OrderBookService service;

    public OrderPlacedConsumer(OrderBookService service) {
        this.service = service;
    }

    @KafkaListener(topics = "orders.placed")
    public void listen(OrderPlacedEvent event){
        Order order = new Order(event.userId(), event.quantity(), event.price(), event.orderSide());
        this.service.processOrder(event.symbol(), order);
    }
}
