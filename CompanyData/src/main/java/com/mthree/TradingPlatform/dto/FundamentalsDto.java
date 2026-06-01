package com.mthree.TradingPlatform.dto;

import java.math.BigDecimal;

public record FundamentalsDto(
        BigDecimal revenue,
        BigDecimal netIncome,
        BigDecimal eps,

        BigDecimal peRatio,
        BigDecimal roe,
        BigDecimal debtToEquity,
        BigDecimal marketCap
) {

}
