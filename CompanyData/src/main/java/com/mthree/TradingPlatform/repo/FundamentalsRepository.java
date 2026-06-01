package com.mthree.TradingPlatform.repo;

import com.mthree.TradingPlatform.domain.model.Fundamentals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FundamentalsRepository extends JpaRepository<Fundamentals, UUID> {
}
