package com.mthree.TradingPlatform.kafka;

import com.mthree.TradingPlatform.events.UnreserveFundsEvent;
import com.mthree.TradingPlatform.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnreserveFundsConsumer {

    private final WalletService walletService;

    @KafkaListener(
            topics = "funds.unreserve",
            groupId = "wallet-service"
    )
    public void process(
            UnreserveFundsEvent event) {

        walletService.unreserveFunds(event);
    }
}
