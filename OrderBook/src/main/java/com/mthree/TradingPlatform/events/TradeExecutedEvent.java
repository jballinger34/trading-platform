package com.mthree.TradingPlatform.events;

import com.mthree.TradingPlatform.domain.model.Trade;

import java.time.Instant;
import java.util.UUID;

public record TradeExecutedEvent(
   UUID eventId,
   Instant timestamp,

   Trade trade
) {}
