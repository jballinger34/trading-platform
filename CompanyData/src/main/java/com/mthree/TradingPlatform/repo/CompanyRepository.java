package com.mthree.TradingPlatform.repo;

import com.mthree.TradingPlatform.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
