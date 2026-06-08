package com.mthree.TradingPlatform.dto;

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
) {}
