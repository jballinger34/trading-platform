package com.mthree.TradingPlatform.service;
import com.mthree.TradingPlatform.events.UnreserveStockEvent;
import com.mthree.TradingPlatform.dto.PortfolioHoldingRequestDto;
import com.mthree.TradingPlatform.dto.PortfolioHoldingResponseDto;
import com.mthree.TradingPlatform.dto.PortfolioSummaryDto;
import com.mthree.TradingPlatform.entity.PortfolioHolding;
import com.mthree.TradingPlatform.events.Trade;
import com.mthree.TradingPlatform.events.TradeExecutedEvent;
import com.mthree.TradingPlatform.repository.PortfolioHoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.mthree.TradingPlatform.events.ReserveStockEvent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioHoldingService {

    private final PortfolioHoldingRepository repository;

    public void processTrade(TradeExecutedEvent event) {
        Trade trade = event.trade();

        String buyerId = trade.buyerUserId().toString();
        String sellerId = trade.sellerUserId().toString();
        String symbol = trade.symbol();
        long quantity = trade.quantity();
        BigDecimal price = trade.price();

        Optional<PortfolioHolding> optionalBuyerHolding = repository.findByUserIdAndSymbol(buyerId, symbol);
        PortfolioHolding buyerHolding;
        if(optionalBuyerHolding.isPresent()){
            buyerHolding = optionalBuyerHolding.get();
            double oldInvestment = buyerHolding.getAveragePrice()*buyerHolding.getQuantity();
            double newInvestment = quantity*price.doubleValue();

            buyerHolding.setQuantity(buyerHolding.getQuantity() + buyerHolding.getQuantity());
            double newAveragePrice = (oldInvestment + newInvestment)/buyerHolding.getQuantity();

            buyerHolding.setAveragePrice(newAveragePrice);
        } else {
            buyerHolding = new PortfolioHolding();
            buyerHolding.setUserId(buyerId);
            buyerHolding.setSymbol(symbol);
            buyerHolding.setQuantity((int) quantity);
            buyerHolding.setAveragePrice(price.doubleValue());
        }
        repository.save(buyerHolding);

        Optional<PortfolioHolding> optionalSellerHolding = repository.findByUserIdAndSymbol(sellerId, symbol);
        PortfolioHolding sellerHolding;
        if(optionalSellerHolding.isPresent()){
            sellerHolding = optionalSellerHolding.get();
            sellerHolding.setReservedQuantity((int) (sellerHolding.getReservedQuantity() - quantity));
        } else {
            throw new RuntimeException("Seller holding not found");
        }
        repository.save(sellerHolding);
    }

    public PortfolioHoldingResponseDto importHolding(PortfolioHoldingRequestDto request, String userId) {

        Optional<PortfolioHolding> holdingOptional = repository.findByUserIdAndSymbol(userId, request.getSymbol());
        PortfolioHolding holding;
        if(holdingOptional.isPresent()){
            holding = holdingOptional.get();
            double oldInvestment = holding.getAveragePrice()*holding.getQuantity();
            double newInvestment = request.getQuantity()*request.getAveragePrice();

            holding.setQuantity(holding.getQuantity() + request.getQuantity());
            double newAveragePrice = (oldInvestment + newInvestment)/holding.getQuantity();

            holding.setAveragePrice(newAveragePrice);
        } else {
            holding = new PortfolioHolding();
            holding.setUserId(userId);
            holding.setSymbol(request.getSymbol());
            holding.setQuantity(request.getQuantity());
            holding.setAveragePrice(request.getAveragePrice());
        }

        PortfolioHolding saved = repository.save(holding);
        return mapToResponse(saved);

    }

    public List<PortfolioHoldingResponseDto> getHoldingsByUserId(
            String userId) {

        return repository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PortfolioHoldingResponseDto mapToResponse(
            PortfolioHolding holding) {

        PortfolioHoldingResponseDto dto =
                new PortfolioHoldingResponseDto();

        dto.setReservedQuantity(
                holding.getReservedQuantity()
        );

        dto.setId(holding.getId());
        dto.setUserId(holding.getUserId());
        dto.setSymbol(holding.getSymbol());
        dto.setQuantity(holding.getQuantity());
        dto.setAveragePrice(holding.getAveragePrice());

        return dto;
    }

    public Integer getHoldingQuantity(
            String userId,
            String symbol) {

        return repository
                .findByUserIdAndSymbol(userId, symbol)
                .map(PortfolioHolding::getQuantity)
                .orElse(0);
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

    public void reserveStock(ReserveStockEvent event) {

        String userId = event.userId().toString();

        PortfolioHolding holding = repository.findByUserIdAndSymbol(userId, event.symbol())
                        .orElseThrow(() -> new RuntimeException("Holding not found"));

        int quantity = event.quantity().intValue();

        if (holding.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient shares");
        }

        holding.setQuantity(holding.getQuantity() - quantity);
        holding.setReservedQuantity(holding.getReservedQuantity() + quantity);

        repository.save(holding);
    }

    public void unreserveStock(UnreserveStockEvent event) {

        String userId = event.userId().toString();

        PortfolioHolding holding = repository.findByUserIdAndSymbol(userId, event.symbol())
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        int quantity = event.quantity().intValue();

        if (holding.getReservedQuantity() < quantity) {
            throw new RuntimeException("Insufficient reserved shares");
        }

        holding.setReservedQuantity(holding.getReservedQuantity() - quantity);
        holding.setQuantity(holding.getQuantity() + quantity);
        repository.save(holding);
    }
}