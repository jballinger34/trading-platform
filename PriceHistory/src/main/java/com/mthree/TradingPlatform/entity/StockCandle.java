package com.mthree.TradingPlatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "stock_candles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"symbol", "bucket_start"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StockCandle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private Instant bucketStart;

    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closePrice;

    private long volume;


}
