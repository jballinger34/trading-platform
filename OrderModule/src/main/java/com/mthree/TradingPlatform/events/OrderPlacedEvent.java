package com.mthree.TradingPlatform.events;

import com.mthree.TradingPlatform.domain.model.OrderSide;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderPlacedEvent(
        UUID eventId,
        Instant timestamp,

        String symbol,

        UUID userId,
        long quantity,
        BigDecimal price,
        OrderSide orderSide
) {

    public static OrderPlacedEvent create(String symbol, UUID userId, long quantity, BigDecimal price, OrderSide orderSide){
        return new OrderPlacedEvent(UUID.randomUUID(), Instant.now(), symbol, userId, quantity, price, orderSide);
    }
}
