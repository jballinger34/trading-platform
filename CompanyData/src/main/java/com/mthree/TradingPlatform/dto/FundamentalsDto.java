package com.mthree.TradingPlatform.dto;

import com.mthree.TradingPlatform.domain.model.Fundamentals;

import java.math.BigDecimal;

public record FundamentalsDto(
        String symbol,

        BigDecimal revenue,
        BigDecimal netIncome,
        BigDecimal eps,

        BigDecimal peRatio,
        BigDecimal roe,
        BigDecimal debtToEquity,
        BigDecimal marketCap
) {
    public static FundamentalsDto from(Fundamentals snapshot){
        return new FundamentalsDto(
                snapshot.getSymbol(),
                snapshot.getRevenue(), snapshot.getNetIncome(), snapshot.getEps(),
                snapshot.getPeRatio(), snapshot.getRoe(), snapshot.getDebtToEquity(), snapshot.getMarketCap());
    }
}
