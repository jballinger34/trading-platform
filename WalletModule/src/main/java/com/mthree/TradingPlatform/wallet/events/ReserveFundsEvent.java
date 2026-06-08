package com.mthree.TradingPlatform.wallet.events;

import java.math.BigDecimal;
import java.util.UUID;

public record ReserveFundsEvent(
        UUID userId,
        BigDecimal amount
) {
}
