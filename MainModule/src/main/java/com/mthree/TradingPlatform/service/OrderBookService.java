package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.domain.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderBookService {

    // TODO
    // order book snapshots for persistence
    //  throw exception for book not found

    //in memory, need to add saving snapshots later
    Map<String, OrderBook> books = new HashMap<>();



    public List<Trade> placeOrder(String symbol, Order order) {
        return getOrCreateOrderBook(symbol).match(order);
    }
    public BigDecimal getHighestBid(String symbol){
        OrderBook book = getOrCreateOrderBook(symbol);
        if(book == null || book.getBids().isEmpty()){
            return null;
        }
        return book.getBids().firstKey();
    }
    public BigDecimal getLowestAsk(String symbol){
        OrderBook book = getOrCreateOrderBook(symbol);
        if(book == null || book.getAsks().isEmpty()){
            return null;
        }
        return book.getAsks().firstKey();
    }
    public void cancelOrder(String symbol, UUID orderId){
        OrderBook book = getOrCreateOrderBook(symbol);
        if(book == null) return;
        book.cancel(orderId);
    }
    public OrderBook getOrCreateOrderBook(String symbol){
        return books.computeIfAbsent(symbol, OrderBook::new);
    }



}
