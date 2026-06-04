package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.CancelOrderRequest;
import com.mthree.TradingPlatform.dto.PlaceOrderRequest;
import com.mthree.TradingPlatform.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestBody PlaceOrderRequest request){
        orderService.placeOrder(request);
        return ResponseEntity.noContent().build();
    }
    @PostMapping ResponseEntity<Void> cancelOrder(@RequestBody CancelOrderRequest request){
        orderService.cancelOrder(request);
        return ResponseEntity.noContent().build();
    }



}
