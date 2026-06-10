package com.mthree.TradingPlatform.manager;

import com.mthree.TradingPlatform.domain.model.*;
import com.mthree.TradingPlatform.repo.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderBookManager {

    private final OrderRepository orderRepository;

    private final Map<String, OrderBook> books = new ConcurrentHashMap<>();

    public OrderBookManager(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersByUser(UUID user){
        return orderRepository.findAllByUserId(user);
    }

    @Transactional
    public List<Trade> match(Order incoming){
        String symbol = incoming.getSymbol();
        OrderBook book = getBook(symbol);

        List<Trade> trades = new ArrayList<>();
        NavigableMap<BigDecimal, Deque<Order>> oppositeSide = (incoming.getSide() == OrderSide.BUY) ? book.getAsks() : book.getBids();

        List<Order> ordersToSave = new ArrayList<>();
        ordersToSave.add(incoming);

        while (incoming.getRemainingQuantity() > 0 && !oppositeSide.isEmpty()) {
            BigDecimal bestPrice = oppositeSide.firstKey();

            boolean crosses = incoming.getSide() == OrderSide.BUY
                    ? incoming.getPrice().compareTo(bestPrice) >= 0
                    : incoming.getPrice().compareTo(bestPrice) <= 0;
            if (!crosses) break;

            Deque<Order> queue = oppositeSide.get(bestPrice);
            if(queue.isEmpty()){
                oppositeSide.remove(bestPrice);
                continue;
            }

            Order resting = queue.peek();

            //get price and amount that we can trade at this price
            BigDecimal tradePrice = resting.getPrice();
            long qty = Math.min(incoming.getRemainingQuantity(), resting.getRemainingQuantity());

            // update quantities and status based off of this
            incoming.reduceQuantity(qty);
            resting.reduceQuantity(qty);

            ordersToSave.add(resting);
            //create trade
            trades.add(Trade.create(
                    incoming.getSide() == OrderSide.BUY ? incoming : resting,
                    incoming.getSide() == OrderSide.BUY ? resting : incoming,
                    symbol,
                    qty,
                    tradePrice
            ));

            // cleanup resting order if filled
            if (!resting.isActive()) {
                book.remove(resting);
            }
        }

        //save the incoming order
        orderRepository.saveAll(ordersToSave);
        if (incoming.isActive()) {
            // add incoming order to book, index it too
            book.add(incoming);
        }
        return trades;
    }

    public Order cancel(String symbol, UUID orderId){
        OrderBook book = getBook(symbol);
        if(book == null) return null;
        Order order = book.getOrderIndex().get(orderId);
        if(order == null) return null;

        order.cancel();

        orderRepository.save(order);
        book.remove(order);

        return order;
    }

    public OrderBook getBook(String symbol) {
        return books.computeIfAbsent(symbol, this::buildBook);
    }
    private OrderBook buildBook(String symbol) {

        OrderBook book = new OrderBook(symbol);

        List<Order> activeOrders =
                orderRepository.findBySymbolAndStatusIn(
                        symbol,
                        List.of(OrderStatus.NEW, OrderStatus.PARTIALLY_FILLED)
                );

        for (Order order : activeOrders) {
            book.add(order);
        }
        return book;
    }


}
