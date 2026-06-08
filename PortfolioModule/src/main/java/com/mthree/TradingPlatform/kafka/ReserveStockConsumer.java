package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.ReserveStockEvent;
import com.mthree.TradingPlatform.service.PortfolioHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveStockConsumer {

    private final PortfolioHoldingService service;

    @KafkaListener(
            topics = "portfolio.reserve-stock",
            groupId = "portfolio-service"
    )
    public void consume(ReserveStockEvent event) {

        service.reserveStock(event);
    }
}
