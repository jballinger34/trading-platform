package com.mthree.TradingPlatform.repository;

import com.mthree.TradingPlatform.entity.PortfolioHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioHoldingRepository
        extends JpaRepository<PortfolioHolding, Long> {

    List<PortfolioHolding> findByUserId(String userId);
}