package com.mthree.TradingPlatform.events;

import java.util.UUID;

public record ReserveStockEvent(
        UUID userId,
        String symbol,
        Long quantity
) {
}
