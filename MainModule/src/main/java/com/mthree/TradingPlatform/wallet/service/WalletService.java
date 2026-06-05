package com.mthree.TradingPlatform.wallet.service;

import com.mthree.TradingPlatform.wallet.events.Trade;
import com.mthree.TradingPlatform.wallet.events.TradeExecutedEvent;
import com.mthree.TradingPlatform.wallet.entity.Wallet;
import com.mthree.TradingPlatform.wallet.events.ReserveFundsEvent;
import com.mthree.TradingPlatform.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;

    public void reserveFunds(ReserveFundsEvent event) {

        String userId = event.userId().toString();

        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));

        double amount = event.amount().doubleValue();

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(
                wallet.getBalance() - amount
        );

        wallet.setReservedBalance(
                wallet.getReservedBalance() + amount
        );

        repository.save(wallet);
    }

    public void processTrade(TradeExecutedEvent event) {

        Trade trade = event.trade();

        String buyerId = trade.buyerUserId().toString();
        String sellerId = trade.sellerUserId().toString();

        double tradeValue =
                trade.price().doubleValue() * trade.quantity();

        Wallet buyerWallet =
                repository.findByUserId(buyerId)
                        .orElseThrow(() ->
                                new RuntimeException("Buyer wallet not found"));

        buyerWallet.setReservedBalance(
                buyerWallet.getReservedBalance() - tradeValue
        );

        repository.save(buyerWallet);

        Wallet sellerWallet =
                repository.findByUserId(sellerId)
                        .orElseThrow(() ->
                                new RuntimeException("Seller wallet not found"));

        sellerWallet.setBalance(
                sellerWallet.getBalance() + tradeValue
        );

        repository.save(sellerWallet);
    }

    public Wallet createWallet(String userId, Double balance) {

        Wallet wallet = new Wallet();

        wallet.setUserId(userId);
        wallet.setBalance(balance);
        wallet.setReservedBalance(0.0);

        return repository.save(wallet);
    }

    public Wallet deposit(
            String userId,
            Double amount) {

        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));

        wallet.setBalance(
                wallet.getBalance() + amount
        );

        return repository.save(wallet);
    }

    public Wallet withdraw(
            String userId,
            Double amount) {

        Wallet wallet = repository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(
                wallet.getBalance() - amount
        );

        return repository.save(wallet);
    }

    public Wallet getWallet(String userId) {

        return repository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));
    }
}