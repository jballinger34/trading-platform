package com.mthree.TradingPlatform.repository;

import com.mthree.TradingPlatform.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    boolean existsBySymbol(String symbol);
}
