package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.dto.PortfolioHoldingRequestDto;
import com.mthree.TradingPlatform.dto.PortfolioHoldingResponseDto;
import com.mthree.TradingPlatform.entity.PortfolioHolding;
import com.mthree.TradingPlatform.dto.PortfolioSummaryDto;
import com.mthree.TradingPlatform.repository.PortfolioHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class PortfolioHoldingService {

    private final PortfolioHoldingRepository repository;

    public PortfolioHoldingResponseDto createHolding(
            PortfolioHoldingRequestDto request) {

        PortfolioHolding holding = new PortfolioHolding();

        holding.setUserId(request.getUserId());
        holding.setInstrumentId(request.getInstrumentId());
        holding.setQuantity(request.getQuantity());
        holding.setAveragePrice(request.getAveragePrice());

        PortfolioHolding saved = repository.save(holding);

        return mapToResponse(saved);
    }

    public List<PortfolioHoldingResponseDto> getAllHoldings() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PortfolioHoldingResponseDto getHoldingById(Long id) {

        PortfolioHolding holding = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Holding not found"));

        return mapToResponse(holding);
    }

    public List<PortfolioHoldingResponseDto> getHoldingsByUserId(
            String userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PortfolioHoldingResponseDto updateHolding(
            Long id,
            PortfolioHoldingRequestDto request) {

        PortfolioHolding holding = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Holding not found"));

        holding.setUserId(request.getUserId());
        holding.setInstrumentId(request.getInstrumentId());
        holding.setQuantity(request.getQuantity());
        holding.setAveragePrice(request.getAveragePrice());

        PortfolioHolding updated = repository.save(holding);

        return mapToResponse(updated);
    }

    public void deleteHolding(Long id) {

        repository.deleteById(id);
    }

    private PortfolioHoldingResponseDto mapToResponse(
            PortfolioHolding holding) {

        PortfolioHoldingResponseDto dto =
                new PortfolioHoldingResponseDto();

        dto.setId(holding.getId());
        dto.setUserId(holding.getUserId());
        dto.setInstrumentId(holding.getInstrumentId());
        dto.setQuantity(holding.getQuantity());
        dto.setAveragePrice(holding.getAveragePrice());

        return dto;
    }

    public PortfolioSummaryDto getPortfolioSummary(String userId) {

        List<PortfolioHolding> holdings =
                repository.findByUserId(userId);

        PortfolioSummaryDto dto =
                new PortfolioSummaryDto();

        dto.setTotalPositions(holdings.size());

        int totalQuantity = holdings.stream()
                .mapToInt(PortfolioHolding::getQuantity)
                .sum();

        double totalInvestment = holdings.stream()
                .mapToDouble(h ->
                        h.getQuantity() * h.getAveragePrice())
                .sum();

        dto.setTotalQuantity(totalQuantity);
        dto.setTotalInvestment(totalInvestment);

        return dto;
    }
}