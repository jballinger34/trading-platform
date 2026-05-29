package com.mthree.TradingPlatform.events;

import com.mthree.TradingPlatform.record.TradeRecord;

import java.time.Instant;
import java.util.UUID;

public record TradeExecutedEvent(
        UUID eventId,
        Instant timestamp,

        TradeRecord trade
) {}
