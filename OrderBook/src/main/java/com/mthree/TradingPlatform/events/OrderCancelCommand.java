package com.mthree.TradingPlatform.events;

import java.util.UUID;

public record OrderCancelCommand(
        UUID userId,
        String symbol,
        UUID orderId
) {
}
