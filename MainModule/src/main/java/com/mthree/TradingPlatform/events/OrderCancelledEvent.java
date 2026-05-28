package com.mthree.TradingPlatform.events;

import java.time.Instant;
import java.util.UUID;

public record OrderCancelledEvent(
   UUID eventId,
   Instant timestamp,

   UUID orderId,

   String symbol
) {}
