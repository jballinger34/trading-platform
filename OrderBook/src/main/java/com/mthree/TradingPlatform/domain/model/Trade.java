package com.mthree.TradingPlatform.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Trade(UUID tradeId, UUID buyerUserId, UUID sellerUserId, String symbol, long quantity, BigDecimal price) {
    public static Trade create(Order bid, Order ask, String symbol, long quantity, BigDecimal price){
        return new Trade(UUID.randomUUID(), bid.getUserId(), ask.getUserId(), symbol, quantity, price);
    }
}
