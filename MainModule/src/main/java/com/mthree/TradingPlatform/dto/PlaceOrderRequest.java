package com.mthree.TradingPlatform.dto;

import com.mthree.TradingPlatform.domain.model.OrderSide;

import java.math.BigDecimal;
import java.util.UUID;

public record PlaceOrderRequest(
        String symbol,

        UUID userId,

        long quantity,
        BigDecimal price,

        OrderSide orderSide

) {
}
