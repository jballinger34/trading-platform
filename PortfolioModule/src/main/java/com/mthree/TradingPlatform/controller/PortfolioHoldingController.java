package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.PortfolioHoldingRequestDto;
import com.mthree.TradingPlatform.dto.PortfolioHoldingResponseDto;
import com.mthree.TradingPlatform.service.PortfolioHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.mthree.TradingPlatform.dto.PortfolioSummaryDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioHoldingController {

    private final PortfolioHoldingService service;

    @GetMapping
    public List<PortfolioHoldingResponseDto> getByUserId(@AuthenticationPrincipal Jwt jwt) {
        return service.getHoldingsByUserId(jwt.getSubject());
    }

    @GetMapping("/quantity")
    public Integer getHoldingQuantity(
            @RequestParam String symbol,
            @AuthenticationPrincipal Jwt jwt) {

        String userId = jwt.getSubject();
        return service.getHoldingQuantity(userId, symbol);
    }

    @GetMapping("/summary")
    public PortfolioSummaryDto getSummary(@AuthenticationPrincipal Jwt jwt) {
        return service.getPortfolioSummary(jwt.getSubject());
    }


    @PostMapping("/import")
    public List<PortfolioHoldingResponseDto> tempAddHolding(@RequestBody List<PortfolioHoldingRequestDto> request, @AuthenticationPrincipal Jwt jwt){
        List<PortfolioHoldingResponseDto> imported = new ArrayList<>();
        for(PortfolioHoldingRequestDto dto : request){
            imported.add(service.importHolding(dto, jwt.getSubject()));
        }
        return imported;
    }

}
