package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.dto.StockCandleDto;
import com.mthree.TradingPlatform.entity.ProcessedEvent;
import com.mthree.TradingPlatform.entity.StockCandle;
import com.mthree.TradingPlatform.events.TradeExecutedEvent;
import com.mthree.TradingPlatform.record.TradeRecord;
import com.mthree.TradingPlatform.repository.StockCandleRepository;
import com.mthree.TradingPlatform.repository.ProcessedEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PriceHistoryService {

    private final ProcessedEventRepository processedEventRepository;
    private final StockCandleRepository stockCandleRepository;

    public PriceHistoryService(ProcessedEventRepository processedEventRepository, StockCandleRepository stockCandleRepository) {
        this.processedEventRepository = processedEventRepository;
        this.stockCandleRepository = stockCandleRepository;
    }

    @Transactional
    public void processTradeExecuted(TradeExecutedEvent event){
        if(processedEventRepository.existsById(event.eventId())) return;

        TradeRecord trade = event.trade();
        Instant bucket = event.timestamp().truncatedTo(ChronoUnit.MINUTES);

        StockCandle candle = stockCandleRepository.findBySymbolAndBucketStart(trade.symbol(), bucket)
                        .orElseGet(() -> createNewCandle(trade, bucket));

        updateCandle(candle, trade);
        stockCandleRepository.save(candle);

        processedEventRepository.save(ProcessedEvent.builder()
                .eventId(event.eventId())
                .processedAt(Instant.now())
                .build());
    }
    public List<StockCandleDto> getBatchLatestPriceBySymbols(List<String> symbols){
        return stockCandleRepository.findLatestPriceBySymbols(symbols).stream().map(StockCandleDto::from).toList();
    }

    public StockCandleDto getLatestCandleBySymbol(String symbol){
        StockCandle candle = stockCandleRepository.findTopBySymbolOrderByBucketStartDesc(symbol).orElseThrow();
        return StockCandleDto.from(candle);
    }
    public List<StockCandleDto> getCandlesBySymbol(String symbol){
        return stockCandleRepository.findBySymbolOrderByBucketStartAsc(symbol).stream().map(StockCandleDto::from).toList();
    }
    public long getVolumeBySymbol(String symbol){
        return stockCandleRepository.getTotalVolumeBySymbol(symbol);
    }


    private StockCandle createNewCandle(TradeRecord trade, Instant bucket ){
        return StockCandle.builder()
                .symbol(trade.symbol())
                .bucketStart(bucket)
                .openPrice(trade.price())
                .highPrice(trade.price())
                .lowPrice(trade.price())
                .closePrice(trade.price())
                .volume(0L)
                .build();
    }
    private void updateCandle(StockCandle c, TradeRecord trade) {
        BigDecimal price = trade.price();

        c.setHighPrice(c.getHighPrice().max(price));
        c.setLowPrice(c.getLowPrice().min(price));
        c.setClosePrice(price);

        c.setVolume(c.getVolume() + trade.quantity());
    }


}
