package com.mthree.TradingPlatform.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceDto(
        String symbol,
        Instant bucketStart,
        BigDecimal close,
        long volume
) {}
