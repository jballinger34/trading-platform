package com.mthree.TradingPlatform.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ScreenedStockDto(
        String symbol,

        String name,
        String sector,
        String industry,

        BigDecimal price,
        Instant priceTime,
        long volume,

        BigDecimal marketCap,
        BigDecimal revenue,
        BigDecimal netIncome,
        BigDecimal eps,

        BigDecimal peRatio,
        BigDecimal roe,
        BigDecimal debtToEquity
) {}
