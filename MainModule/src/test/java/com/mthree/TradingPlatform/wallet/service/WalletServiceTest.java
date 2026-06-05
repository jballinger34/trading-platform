package com.mthree.TradingPlatform.wallet.service;

import com.mthree.TradingPlatform.wallet.entity.Wallet;
import com.mthree.TradingPlatform.wallet.events.ReserveFundsEvent;
import com.mthree.TradingPlatform.wallet.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository repository;

    @InjectMocks
    private WalletService walletService;

    @Test
    void reserveFundsMovesBalanceToReservedBalance() {

        String userId = UUID.randomUUID().toString();

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(1000.0);
        wallet.setReservedBalance(0.0);

        when(repository.findByUserId(userId))
                .thenReturn(Optional.of(wallet));

        ReserveFundsEvent event =
                new ReserveFundsEvent(
                        UUID.fromString(userId),
                        BigDecimal.valueOf(200)
                );

        walletService.reserveFunds(event);

        assertEquals(800.0, wallet.getBalance());
        assertEquals(200.0, wallet.getReservedBalance());

        verify(repository).save(wallet);
    }

    @Test
    void reserveFundsThrowsWhenInsufficientBalance() {

        String userId = UUID.randomUUID().toString();

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(100.0);
        wallet.setReservedBalance(0.0);

        when(repository.findByUserId(userId))
                .thenReturn(Optional.of(wallet));

        ReserveFundsEvent event =
                new ReserveFundsEvent(
                        UUID.fromString(userId),
                        BigDecimal.valueOf(500)
                );

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> walletService.reserveFunds(event)
                );

        assertEquals(
                "Insufficient balance",
                exception.getMessage()
        );
    }

    @Test
    void createWalletCreatesNewWallet() {

        Wallet wallet = new Wallet();
        wallet.setUserId("user1");
        wallet.setBalance(1000.0);

        when(repository.save(any(Wallet.class)))
                .thenReturn(wallet);

        Wallet result =
                walletService.createWallet(
                        "user1",
                        1000.0
                );

        assertEquals("user1", result.getUserId());
        assertEquals(1000.0, result.getBalance());

        verify(repository).save(any(Wallet.class));
    }

    @Test
    void depositIncreasesBalance() {

        Wallet wallet = new Wallet();

        wallet.setUserId("user1");
        wallet.setBalance(1000.0);

        when(repository.findByUserId("user1"))
                .thenReturn(Optional.of(wallet));

        walletService.deposit(
                "user1",
                500.0
        );

        assertEquals(
                1500.0,
                wallet.getBalance()
        );

        verify(repository).save(wallet);
    }

    @Test
    void withdrawReducesBalance() {

        Wallet wallet = new Wallet();

        wallet.setUserId("user1");
        wallet.setBalance(1000.0);

        when(repository.findByUserId("user1"))
                .thenReturn(Optional.of(wallet));

        walletService.withdraw(
                "user1",
                300.0
        );

        assertEquals(
                700.0,
                wallet.getBalance()
        );

        verify(repository).save(wallet);
    }

    @Test
    void withdrawThrowsWhenInsufficientBalance() {

        Wallet wallet = new Wallet();

        wallet.setUserId("user1");
        wallet.setBalance(100.0);

        when(repository.findByUserId("user1"))
                .thenReturn(Optional.of(wallet));

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> walletService.withdraw(
                                "user1",
                                500.0
                        )
                );

        assertEquals(
                "Insufficient balance",
                exception.getMessage()
        );
    }
}