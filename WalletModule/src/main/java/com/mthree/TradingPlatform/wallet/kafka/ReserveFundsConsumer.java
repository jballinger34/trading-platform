package com.mthree.TradingPlatform.wallet.kafka;

import com.mthree.TradingPlatform.wallet.events.ReserveFundsEvent;
import com.mthree.TradingPlatform.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveFundsConsumer {

    private final WalletService walletService;

    @KafkaListener(
            topics = "wallet.reserve",
            groupId = "wallet-service"
    )
    public void consume(ReserveFundsEvent event) {

        walletService.reserveFunds(event);
    }
}
