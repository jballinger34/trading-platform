package com.mthree.TradingPlatform.domain.model;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import java.math.BigDecimal;
import java.util.*;


public class OrderBook {
    private final String symbol;

    @Getter
    private final NavigableMap<BigDecimal, Deque<Order>> bids = new TreeMap<>(Comparator.reverseOrder());
    @Getter
    private final NavigableMap<BigDecimal, Deque<Order>> asks = new TreeMap<>();

    // fast lookup for cancel
    @Getter
    private final Map<UUID, Order> orderIndex = new HashMap<>();

    public OrderBook(String symbol) {
        this.symbol = symbol;
    }

    public void add(Order order) {
        if (!order.isActive()) {
            return;
        }

        orderIndex.put(order.getOrderId(), order);
        if (order.getSide() == OrderSide.BUY) {
            bids.computeIfAbsent(order.getPrice(), p -> new ArrayDeque<>()).addLast(order); // FIFO time priority
        } else {
            asks.computeIfAbsent(order.getPrice(), p -> new ArrayDeque<>()).addLast(order);
        }
    }
    public void remove(Order order){
        if(order.isActive()) return;

        orderIndex.remove(order.getOrderId());
        NavigableMap<BigDecimal, Deque<Order>> oppositeSide = getBids();
        if(order.getSide() == OrderSide.BUY){
            oppositeSide = getAsks();
        }

        BigDecimal price = order.getPrice();
        Deque<Order> queue = oppositeSide.get(price);
        queue.remove(order);

        if (queue.isEmpty()) {
            oppositeSide.remove(price);
        }
    }



}
