package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.UnreserveStockEvent;
import com.mthree.TradingPlatform.service.PortfolioHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnreserveStockConsumer {

    private final PortfolioHoldingService service;

    @KafkaListener(topics = "portfolio.unreserve")
    public void process(UnreserveStockEvent event) {
        service.unreserveStock(event);
    }
}