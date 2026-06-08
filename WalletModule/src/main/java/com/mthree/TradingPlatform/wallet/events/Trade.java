package com.mthree.TradingPlatform.wallet.events;

import java.math.BigDecimal;
import java.util.UUID;

public record Trade(
        UUID buyerUserId,
        UUID sellerUserId,
        String symbol,
        long quantity,
        BigDecimal price
) {
}