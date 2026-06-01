package com.mthree.TradingPlatform.repo;

import com.mthree.TradingPlatform.domain.model.CompanyInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MappingRepository extends JpaRepository<CompanyInstrument, UUID> {
}
