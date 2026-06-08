package com.mthree.TradingPlatform.dto;

import com.mthree.TradingPlatform.domain.model.Order;
import com.mthree.TradingPlatform.domain.model.OrderSide;
import com.mthree.TradingPlatform.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderDto(
        UUID orderId,

        UUID userId,
        String symbol,

        long quantity,
        long remainingQuantity,

        BigDecimal price,
        OrderSide side,

        Instant createdAt,

        OrderStatus status
) {
    public static OrderDto from(Order order){
        return new OrderDto(
                order.getOrderId(),

                order.getUserId(),
                order.getSymbol(),

                order.getQuantity(),
                order.getRemainingQuantity(),

                order.getPrice(),
                order.getSide(),

                order.getCreatedAt(),
                order.getStatus()
        );
    }
}
