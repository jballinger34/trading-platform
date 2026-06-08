package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.CreateWalletRequest;
import com.mthree.TradingPlatform.entity.Wallet;
import com.mthree.TradingPlatform.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.mthree.TradingPlatform.dto.WalletTransactionRequest;
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public Wallet createWallet(

            @RequestBody CreateWalletRequest request) {

        return walletService.createWallet(
                request.getUserId(),
                request.getBalance()



        );
    }

    @PostMapping("/deposit")
    public Wallet deposit(
            @RequestBody WalletTransactionRequest request) {

        return walletService.deposit(
                request.getUserId(),
                request.getAmount()
        );
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(
            @RequestBody WalletTransactionRequest request) {

        return walletService.withdraw(
                request.getUserId(),
                request.getAmount()
        );
    }
    @GetMapping("/{userId}")
    public Wallet getWallet(
            @PathVariable String userId) {

        return walletService.getWallet(userId);
    }
}