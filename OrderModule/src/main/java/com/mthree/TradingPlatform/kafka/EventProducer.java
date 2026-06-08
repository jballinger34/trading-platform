package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.OrderCancelCommand;
import com.mthree.TradingPlatform.events.OrderPlacedEvent;
import com.mthree.TradingPlatform.events.ReserveFundsEvent;
import com.mthree.TradingPlatform.events.ReserveStockEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private  <T> void publish(String topic, T event) {
        kafkaTemplate.send(topic, event);
    }
    public void publishReserveFunds(ReserveFundsEvent event){
        publish("portfolio.reserve", event);
    }
    public void publishReserveStock(ReserveStockEvent event){
        publish("wallet.reserve", event);
    }

    public void publishOrderPlaced(OrderPlacedEvent event){
        publish("orders.placed", event);
    }
    public void publishCancelOrder(OrderCancelCommand command){
        publish("orders.cancel", command);
    }

}
