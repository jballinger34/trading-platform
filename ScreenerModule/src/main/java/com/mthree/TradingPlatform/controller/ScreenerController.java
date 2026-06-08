package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.ScreenResultDto;
import com.mthree.TradingPlatform.request.StockScreenRequest;
import com.mthree.TradingPlatform.service.ScreenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/screener")
public class ScreenerController {

    private final ScreenerService service;

    public ScreenerController(ScreenerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ScreenResultDto> screenStocks(StockScreenRequest request){
        ScreenResultDto results = service.screen(request);
        return ResponseEntity.ok(results);
    }




}

