package com.mthree.TradingPlatform.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Trade(UUID tradeId, UUID buyOrderId, UUID sellOrderId, String symbol, long quantity, BigDecimal price, Instant timestamp) {
    public static Trade create(Order bid, Order ask, String symbol, long quantity, BigDecimal price){
        return new Trade(UUID.randomUUID(), bid.getOrderId(), ask.getOrderId(), symbol, quantity, price, Instant.now());
    }
}
