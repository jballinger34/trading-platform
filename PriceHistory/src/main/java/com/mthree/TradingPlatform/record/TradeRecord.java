package com.mthree.TradingPlatform.record;

import java.math.BigDecimal;

public record TradeRecord(String symbol, long quantity, BigDecimal price) {}
