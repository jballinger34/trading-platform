package com.mthree.TradingPlatform.dto;

import com.mthree.TradingPlatform.entity.StockCandle;

import java.math.BigDecimal;
import java.time.Instant;

public record StockCandleDto (
        String symbol,
        Instant bucketStart,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        long volume
) {
    public static StockCandleDto from(StockCandle candle){
        return new StockCandleDto(
                candle.getSymbol(),
                candle.getBucketStart(),
                candle.getOpenPrice(),
                candle.getHighPrice(),
                candle.getLowPrice(),
                candle.getLowPrice(),
                candle.getVolume()
        );
    }

}
