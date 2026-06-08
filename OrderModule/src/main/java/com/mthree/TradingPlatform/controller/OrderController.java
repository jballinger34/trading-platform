package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.requests.CancelOrderRequest;
import com.mthree.TradingPlatform.requests.PlaceOrderRequest;
import com.mthree.TradingPlatform.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> placeOrder(@RequestBody PlaceOrderRequest request, @AuthenticationPrincipal Jwt jwt){
        UUID userId = UUID.fromString(jwt.getSubject());
        orderService.placeOrder(userId, request);

        return ResponseEntity.noContent().build();
    }
    @PostMapping("/cancel")
    ResponseEntity<Void> cancelOrder(@RequestBody CancelOrderRequest request, @AuthenticationPrincipal Jwt jwt){
        UUID userId = UUID.fromString(jwt.getSubject());
        orderService.cancelOrder(userId, request);

        return ResponseEntity.noContent().build();
    }



}
