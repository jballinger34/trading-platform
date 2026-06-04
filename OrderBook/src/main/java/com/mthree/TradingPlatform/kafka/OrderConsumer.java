package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.domain.model.Order;
import com.mthree.TradingPlatform.events.OrderCancelCommand;
import com.mthree.TradingPlatform.events.OrderPlacedEvent;
import com.mthree.TradingPlatform.service.OrderBookService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    
    private final OrderBookService service;

    public OrderConsumer(OrderBookService service) {
        this.service = service;
    }

    @KafkaListener(topics = "orders.placed")
    public void listen(OrderPlacedEvent event){
        Order order = new Order(event.userId(), event.quantity(), event.price(), event.orderSide());
        this.service.processOrder(event.symbol(), order);
    }

    @KafkaListener(topics = "orders.cancel")
    public void listen(OrderCancelCommand command){
        this.service.cancelOrder(command.symbol(), command.orderId());
    }

}
