package com.mthree.TradingPlatform.requests;

import com.mthree.TradingPlatform.domain.model.OrderSide;

import java.math.BigDecimal;
import java.util.UUID;

public record PlaceOrderRequest(
        String symbol,

        long quantity,
        BigDecimal price,

        OrderSide orderSide

) {
}
