package com.mthree.TradingPlatform.domain.model;

import lombok.Getter;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.*;


public class OrderBook {
    private final String symbol;

    @Getter
    private final NavigableMap<BigDecimal, Deque<Order>> bids = new TreeMap<>(Comparator.reverseOrder());
    @Getter
    private final NavigableMap<BigDecimal, Deque<Order>> asks = new TreeMap<>();

    // fast lookup for cancel/modify
    private final Map<UUID, Order> orderIndex = new HashMap<>();

    public OrderBook(String symbol) {
        this.symbol = symbol;
    }

    public List<Trade> match(Order incoming) {
        List<Trade> trades = new ArrayList<>();

        NavigableMap<BigDecimal, Deque<Order>> oppositeBook = (incoming.getSide() == OrderSide.BUY) ? asks : bids;

        while (incoming.getRemainingQuantity() > 0 && !oppositeBook.isEmpty()) {

            BigDecimal bestPrice = oppositeBook.firstKey();

            // price check
            boolean crosses = incoming.getSide() == OrderSide.BUY
                            ? incoming.getPrice().compareTo(bestPrice) >= 0
                            : incoming.getPrice().compareTo(bestPrice) <= 0;

            if (!crosses) break;

            Deque<Order> queue = oppositeBook.get(bestPrice);
            Order resting = queue.peek();

            long qty = Math.min(incoming.getRemainingQuantity(), resting.getRemainingQuantity());

            // update quantities
            incoming.setRemainingQuantity(incoming.getRemainingQuantity() - qty);
            resting.setRemainingQuantity(resting.getRemainingQuantity() - qty);

            incoming.updateOrderStatus();
            resting.updateOrderStatus();

            // trade price = resting order price
            BigDecimal tradePrice = resting.getPrice();

            trades.add(Trade.create(
                    incoming.getSide() == OrderSide.BUY ? incoming : resting,
                    incoming.getSide() == OrderSide.BUY ? resting : incoming,
                    symbol,
                    qty,
                    tradePrice
            ));

            // cleanup resting order if filled
            if (resting.getRemainingQuantity() == 0) {
                queue.poll();
                orderIndex.remove(resting.getOrderId());
            }

            if (queue.isEmpty()) {
                oppositeBook.remove(bestPrice);
            }
        }

        // store leftover incoming order
        // index incoming order (if not already fully filled)
        if (incoming.getRemainingQuantity() > 0) {
            addOrder(incoming);
        }

        return trades;
    }
    private void addOrder(Order order) {

        // ensure order is ready for resting book
        if (order.getRemainingQuantity() <= 0) {
            return;
        }

        orderIndex.put(order.getOrderId(), order);

        if (order.getSide() == OrderSide.BUY) {
            bids.computeIfAbsent(order.getPrice(), p -> new ArrayDeque<>()).add(order);

        } else {
            asks.computeIfAbsent(order.getPrice(), p -> new ArrayDeque<>()).add(order);
        }
    }

    public void cancel(UUID orderId) {

        Order order = orderIndex.get(orderId);
        if (order == null) return;
        order.setCancelled();


        NavigableMap<BigDecimal, Deque<Order>> book =
                (order.getSide() == OrderSide.BUY) ? bids : asks;

        Deque<Order> queue = book.get(order.getPrice());

        queue.remove(order);
        if(queue.isEmpty()){
            book.remove(order.getPrice());
        }

        orderIndex.remove(orderId);
    }

}
