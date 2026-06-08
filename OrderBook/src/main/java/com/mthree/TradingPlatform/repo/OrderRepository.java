package com.mthree.TradingPlatform.repo;

import com.mthree.TradingPlatform.domain.model.Order;
import com.mthree.TradingPlatform.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findBySymbolAndStatusIn(String symbol, List<OrderStatus> statuses);

    List<Order> findAllByUserId(UUID userId);
}
