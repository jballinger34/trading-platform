package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.StockCandleDto;
import com.mthree.TradingPlatform.service.PriceHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-history")
public class PriceHistoryController {

    private final PriceHistoryService service;

    public PriceHistoryController(PriceHistoryService service) {
        this.service = service;
    }

    @GetMapping("/latest/{symbol}")
    public ResponseEntity<StockCandleDto> getLatestCandle(@PathVariable String symbol){
        StockCandleDto candle = service.getLatestCandleBySymbol(symbol);
        return ResponseEntity.ok(candle);
    }
    @GetMapping("/latest")
    public ResponseEntity<List<StockCandleDto>> getBatchLatestCandle(List<String> symbols){
        List<StockCandleDto> candle = service.getBatchLatestPriceBySymbols(symbols);
        return ResponseEntity.ok(candle);
    }

    @GetMapping("/volume/{symbol}")
    public ResponseEntity<Long> getTotalVolume(@PathVariable String symbol){

        long totalVolume = service.getVolumeBySymbol(symbol);

        return ResponseEntity.ok(totalVolume);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockCandleDto>> getCandles(@PathVariable String symbol){
        List<StockCandleDto> candles = service.getCandlesBySymbol(symbol);
        return ResponseEntity.ok(candles);
    }



}
