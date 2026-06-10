package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.event.InstrumentCreatedEvent;
import com.mthree.TradingPlatform.service.CompanyDataService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InstrumentCreatedConsumer {

    private final CompanyDataService service;

    public InstrumentCreatedConsumer(CompanyDataService service) {
        this.service = service;
    }

    @Transactional
    @KafkaListener(topics = "instrument.created")
    public void handle(InstrumentCreatedEvent event){
        service.ingestInstrument(event);
    }
}
