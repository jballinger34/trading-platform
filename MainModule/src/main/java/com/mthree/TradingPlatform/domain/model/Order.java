package com.mthree.TradingPlatform.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Order {

    private final UUID orderId;
    private final UUID userId;

    private final long quantity;
    @Setter
    private long remainingQuantity;

    private final BigDecimal price;
    private final OrderSide side;

    private final Instant timestamp;

    private OrderStatus status;

    public Order(UUID userId, long quantity, BigDecimal price, OrderSide side) {
        this.orderId = UUID.randomUUID();
        this.userId = userId;

        this.quantity = quantity;
        this.remainingQuantity = quantity;

        this.price = price;
        this.side = side;

        this.timestamp = Instant.now();
        this.status = OrderStatus.NEW;
    }

    public void updateOrderStatus(){
        if(remainingQuantity == quantity){
            this.status = OrderStatus.NEW;
        } else if (remainingQuantity == 0){
            this.status = OrderStatus.FILLED;
        } else {
            this.status = OrderStatus.PARTIALLY_FILLED;
        }
    }

    public void setCancelled() {
        this.status = OrderStatus.CANCELLED;
    }
}
