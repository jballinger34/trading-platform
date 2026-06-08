package com.mthree.TradingPlatform.wallet.events;

import java.time.Instant;
import java.util.UUID;

public record TradeExecutedEvent(
        UUID eventId,
        Instant timestamp,
        Trade trade
) {
}
