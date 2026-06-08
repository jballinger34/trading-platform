package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.entity.PortfolioHolding;
import com.mthree.TradingPlatform.events.ReserveStockEvent;
import com.mthree.TradingPlatform.events.UnreserveStockEvent;
import com.mthree.TradingPlatform.repository.PortfolioHoldingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioHoldingServiceTest {

    @Mock
    private PortfolioHoldingRepository repository;

    @InjectMocks
    private PortfolioHoldingService service;

    @Test
    void reserveStockMovesSharesToReserved() {

        String userId = UUID.randomUUID().toString();

        PortfolioHolding holding = new PortfolioHolding();
        holding.setUserId(userId);
        holding.setSymbol("AAPL");
        holding.setQuantity(100);
        holding.setReservedQuantity(0);

        when(repository.findByUserIdAndSymbol(
                userId,
                "AAPL"
        )).thenReturn(Optional.of(holding));

        ReserveStockEvent event =
                new ReserveStockEvent(
                        UUID.fromString(userId),
                        "AAPL",
                        20L
                );

        service.reserveStock(event);

        assertEquals(80, holding.getQuantity());
        assertEquals(20, holding.getReservedQuantity());

        verify(repository).save(holding);
    }

    @Test
    void reserveStockThrowsWhenInsufficientShares() {

        String userId = UUID.randomUUID().toString();

        PortfolioHolding holding = new PortfolioHolding();
        holding.setUserId(userId);
        holding.setSymbol("AAPL");
        holding.setQuantity(10);
        holding.setReservedQuantity(0);

        when(repository.findByUserIdAndSymbol(
                userId,
                "AAPL"
        )).thenReturn(Optional.of(holding));

        ReserveStockEvent event =
                new ReserveStockEvent(
                        UUID.fromString(userId),
                        "AAPL",
                        50L
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.reserveStock(event)
                );

        assertEquals(
                "Insufficient shares",
                exception.getMessage()
        );
    }

    @Test
    void unreserveStockMovesReservedSharesBack() {

        String userId = UUID.randomUUID().toString();

        PortfolioHolding holding = new PortfolioHolding();
        holding.setUserId(userId);
        holding.setSymbol("AAPL");
        holding.setQuantity(80);
        holding.setReservedQuantity(20);

        when(repository.findByUserIdAndSymbol(
                userId,
                "AAPL"
        )).thenReturn(Optional.of(holding));

        UnreserveStockEvent event =
                new UnreserveStockEvent(
                        UUID.fromString(userId),
                        "AAPL",
                        10L
                );

        service.unreserveStock(event);

        assertEquals(90, holding.getQuantity());
        assertEquals(10, holding.getReservedQuantity());

        verify(repository).save(holding);
    }

    @Test
    void unreserveStockThrowsWhenInsufficientReservedShares() {

        String userId = UUID.randomUUID().toString();

        PortfolioHolding holding = new PortfolioHolding();
        holding.setUserId(userId);
        holding.setSymbol("AAPL");
        holding.setQuantity(80);
        holding.setReservedQuantity(5);

        when(repository.findByUserIdAndSymbol(
                userId,
                "AAPL"
        )).thenReturn(Optional.of(holding));

        UnreserveStockEvent event =
                new UnreserveStockEvent(
                        UUID.fromString(userId),
                        "AAPL",
                        20L
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.unreserveStock(event)
                );

        assertEquals(
                "Insufficient reserved shares",
                exception.getMessage()
        );
    }
}