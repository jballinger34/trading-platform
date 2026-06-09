package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.event.InstrumentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InstrumentCreatedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InstrumentCreatedProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private  <T> void publish(String topic, T event) {
        kafkaTemplate.send(topic, event);
    }

    public void publishInstrumentCreated(InstrumentCreatedEvent event){
        publish("instrument.created", event);
    }
}
