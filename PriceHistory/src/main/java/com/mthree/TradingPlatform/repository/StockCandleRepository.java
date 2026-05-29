package com.mthree.TradingPlatform.repository;

import com.mthree.TradingPlatform.entity.StockCandle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockCandleRepository extends JpaRepository<StockCandle, Long> {

    Optional<StockCandle> findBySymbolAndBucketStart(String symbol, Instant bucketStart);

    List<StockCandle> findBySymbolOrderByBucketStartAsc(String symbol);

    @Query("""
        SELECT COALESCE(SUM(c.volume), 0)
        FROM StockCandle c
        WHERE c.symbol = :symbol
    """)
    long getTotalVolumeBySymbol(String symbol);
}
