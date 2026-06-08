package com.mthree.TradingPlatform.events;

import java.time.Instant;
import java.util.UUID;

public record OrderCancelSuccessEvent(
   UUID eventId,
   Instant timestamp,

   UUID orderId,

   String symbol
) {}
