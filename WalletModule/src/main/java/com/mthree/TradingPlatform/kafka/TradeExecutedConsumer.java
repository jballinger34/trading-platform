package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.TradeExecutedEvent;
import com.mthree.TradingPlatform.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeExecutedConsumer {

    private final WalletService walletService;

    @KafkaListener(
            topics = "trades.executed",
            groupId = "wallet-service"
    )
    public void consume(TradeExecutedEvent event) {

        walletService.processTrade(event);
    }
}