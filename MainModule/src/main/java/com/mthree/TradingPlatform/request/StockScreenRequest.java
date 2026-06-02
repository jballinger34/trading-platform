package com.mthree.TradingPlatform.request;

import java.math.BigDecimal;

public record StockScreenRequest(
        String sector,
        String industry,

        BigDecimal minPrice,
        BigDecimal maxPrice,

        BigDecimal minVolume,
        BigDecimal maxVolume,

        BigDecimal minRevenue,
        BigDecimal maxRevenue,

        BigDecimal minNetIncome,
        BigDecimal maxNetIncome,

        BigDecimal minEps,
        BigDecimal maxEps,

        BigDecimal minPe,
        BigDecimal maxPe,

        BigDecimal minRoe,
        BigDecimal maxRoe,

        BigDecimal minDebtToEquity,
        BigDecimal maxDebtToEquity,

        Long minMarketCap,
        Long maxMarketCap,

        Integer page,
        Integer size,

        String sortBy,
        String direction
) {}