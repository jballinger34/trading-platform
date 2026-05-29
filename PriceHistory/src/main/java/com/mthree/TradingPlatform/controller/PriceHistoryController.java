package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.StockCandleDto;
import com.mthree.TradingPlatform.entity.StockCandle;
import com.mthree.TradingPlatform.service.PriceHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PriceHistoryController {

    private final PriceHistoryService service;

    public PriceHistoryController(PriceHistoryService service) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockCandleDto>> getCandles(@PathVariable String symbol){
        List<StockCandleDto> candles = service.getCandlesBySymbol(symbol);
        return ResponseEntity.ok(candles);
    }
    @GetMapping("/{symbol}/volume")
    public ResponseEntity<Long> getVolume(@PathVariable String symbol){

        long totalVolume = service.getVolumeBySymbol(symbol);

        return ResponseEntity.ok(totalVolume);
    }


}
