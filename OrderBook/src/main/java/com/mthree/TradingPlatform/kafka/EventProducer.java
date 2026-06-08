package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.*;
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
    private  <T> void publish(String topic, String key, T event) {
        kafkaTemplate.send(topic, key, event);
    }

    public void publishOrderProcessed(OrderProcessedEvent event){
        publish("orders.processed", event.orderId().toString(), event);
    }
    public void publishTradeExecuted(TradeExecutedEvent event){
        publish("trades.executed", event.trade().symbol(), event);
    }

    public void publishUnreserveStock(UnreserveStockEvent event){
        publish("portfolio.unreserve", event);
    }
    public void publicUnreserveFunds(UnreserveFundsEvent event){
        publish("wallet.unreserve", event);
    }


    public void publishOrderCancelled(OrderCancelSuccessEvent event){
        publish("orders.cancel.success", event.orderId().toString() ,event);
    }

}
