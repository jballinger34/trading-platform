package com.mthree.TradingPlatform.events;

import java.util.UUID;

public record OrderCancelCommand(
        String symbol,
        UUID orderId
) {
}
