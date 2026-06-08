package com.mthree.TradingPlatform.events;

import java.math.BigDecimal;
import java.util.UUID;

public record ReserveFundsEvent(
        UUID userId,
        BigDecimal amount
){}
