package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.ScreenedStockDto;
import com.mthree.TradingPlatform.request.StockScreenRequest;
import com.mthree.TradingPlatform.service.ScreenerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screener")
public class ScreenerController {

    private final ScreenerService service;

    public ScreenerController(ScreenerService service) {
        this.service = service;
    }

    @GetMapping
    public List<ScreenedStockDto> screenStocks(StockScreenRequest request){
        return service.screen(request);
    }




}

