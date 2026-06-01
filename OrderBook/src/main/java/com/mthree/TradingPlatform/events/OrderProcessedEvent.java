package com.mthree.TradingPlatform.events;

import com.mthree.TradingPlatform.domain.model.OrderSide;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderProcessedEvent(
        UUID eventId,
        Instant timestamp,

        UUID orderId,
        UUID userId,

        String symbol,
        OrderSide orderSide,

        BigDecimal price,
        long quantity
) {}
