package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.FundamentalsDto;
import com.mthree.TradingPlatform.service.CompanyDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fundamentals")
public class FundamentalsController {

    private final CompanyDataService companyDataService;

    public FundamentalsController(CompanyDataService companyDataService) {
        this.companyDataService = companyDataService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<FundamentalsDto> getFundamentals(@PathVariable String symbol){
        FundamentalsDto dto = companyDataService.getFundamentalsBySymbol(symbol);
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<FundamentalsDto>> getBatchFundamentals(@RequestParam List<String> symbols){
        List<FundamentalsDto> dtos = companyDataService.getFundamentalsBySymbols(symbols);
        return ResponseEntity.ok(dtos);
    }

}
