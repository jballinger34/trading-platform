package com.mthree.TradingPlatform.domain.model;

import com.mthree.TradingPlatform.events.OrderPlacedEvent;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private UUID orderId;

    @Version
    private Long version;

    private UUID userId;

    private String symbol;

    private long quantity;

    private long remainingQuantity;

    private BigDecimal price;
    private OrderSide side;

    private Instant createdAt;

    private OrderStatus status;


    public boolean isActive() {
        return status == OrderStatus.NEW || status == OrderStatus.PARTIALLY_FILLED;
    }
    public void reduceQuantity(long qty) {
        this.remainingQuantity -= qty;

        if (this.remainingQuantity == 0) {
            this.status = OrderStatus.FILLED;
        } else {
            this.status = OrderStatus.PARTIALLY_FILLED;
        }
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    public static Order build(OrderPlacedEvent event){
        Order order = new Order();
        order.userId = event.userId();
        order.symbol = event.symbol();

        order.quantity = event.quantity();
        order.remainingQuantity = event.quantity();

        order.price = event.price();
        order.side = event.orderSide();
        order.createdAt = Instant.now();
        order.status = OrderStatus.NEW;
        return order;
    }
}
