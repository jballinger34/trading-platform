package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.TradeExecutedEvent;
import com.mthree.TradingPlatform.service.PriceHistoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TradeExecutedConsumer {

    private final PriceHistoryService service;

    public TradeExecutedConsumer(PriceHistoryService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "trades.executed"
    )
    public void listen(TradeExecutedEvent event){
        service.processTradeExecuted(event);
    }
}
