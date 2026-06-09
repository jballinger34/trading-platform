package com.mthree.TradingPlatform.controller;


import com.mthree.TradingPlatform.dto.OrderDto;
import com.mthree.TradingPlatform.service.OrderBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-book")
public class OrderBookController {

    private final OrderBookService orderBookService;

    public OrderBookController(OrderBookService orderBookService) {
        this.orderBookService = orderBookService;
    }

    @RequestMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@AuthenticationPrincipal Jwt jwt){
        UUID uuid = UUID.fromString(jwt.getSubject());

        List<OrderDto> orderDtoList = orderBookService.getOrdersByUser(uuid);
        return ResponseEntity.ok(orderDtoList);
    }
}
