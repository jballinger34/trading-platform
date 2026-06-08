package com.mthree.TradingPlatform.wallet.events;

import java.math.BigDecimal;
import java.util.UUID;

public record UnreserveFundsEvent(
        UUID userId,
        BigDecimal amount
) {
}