package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.PortfolioHoldingRequestDto;
import com.mthree.TradingPlatform.dto.PortfolioHoldingResponseDto;
import com.mthree.TradingPlatform.service.PortfolioHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.mthree.TradingPlatform.dto.PortfolioSummaryDto;
import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioHoldingController {

    private final PortfolioHoldingService service;

    @PostMapping
    public PortfolioHoldingResponseDto createHolding(
            @RequestBody PortfolioHoldingRequestDto request) {

        return service.createHolding(request);
    }

    @GetMapping
    public List<PortfolioHoldingResponseDto> getAllHoldings() {

        return service.getAllHoldings();
    }

    @GetMapping("/{id}")
    public PortfolioHoldingResponseDto getHoldingById(
            @PathVariable Long id) {

        return service.getHoldingById(id);
    }

    @GetMapping("/quantity")
    public Integer getHoldingQuantity(
            @RequestParam String userId,
            @RequestParam String symbol) {

        return service.getHoldingQuantity(userId, symbol);
    }

    @GetMapping("/user/{userId}")
    public List<PortfolioHoldingResponseDto> getByUserId(
            @PathVariable String userId) {

        return service.getHoldingsByUserId(userId);
    }

    @GetMapping("/summary/{userId}")
    public PortfolioSummaryDto getSummary(
            @PathVariable String userId) {

        return service.getPortfolioSummary(userId);
    }

    @PutMapping("/{id}")
    public PortfolioHoldingResponseDto updateHolding(
            @PathVariable Long id,
            @RequestBody PortfolioHoldingRequestDto request) {

        return service.updateHolding(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHolding(@PathVariable Long id) {

        service.deleteHolding(id);
    }
}
