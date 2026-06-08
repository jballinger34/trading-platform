package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;
import com.mthree.TradingPlatform.service.CompanyDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company-profile")
public class ProfileController {

    private final CompanyDataService companyDataService;

    public ProfileController(CompanyDataService companyDataService) {
        this.companyDataService = companyDataService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<CompanyProfileDto> getProfile(@PathVariable String symbol){
        CompanyProfileDto dto = companyDataService.getProfileBySymbol(symbol);
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<CompanyProfileDto>> getBatchFundamentals(@RequestParam List<String> symbols){
        List<CompanyProfileDto> dtos = companyDataService.getProfilesBySymbols(symbols);
        return ResponseEntity.ok(dtos);
    }

}
