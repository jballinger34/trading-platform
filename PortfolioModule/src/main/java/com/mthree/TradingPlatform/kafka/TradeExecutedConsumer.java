package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.TradeExecutedEvent;
import com.mthree.TradingPlatform.service.PortfolioHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeExecutedConsumer {

    private final PortfolioHoldingService portfolioHoldingService;

    @KafkaListener(topics = "trades.executed")
    public void processTrade(TradeExecutedEvent event) {
        portfolioHoldingService.processTrade(event);
    }
}
