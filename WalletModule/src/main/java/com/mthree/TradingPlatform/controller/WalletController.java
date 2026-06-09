package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.entity.Wallet;
import com.mthree.TradingPlatform.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.mthree.TradingPlatform.dto.WalletTransactionRequest;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/deposit")
    public Wallet deposit(@RequestBody WalletTransactionRequest request, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return walletService.deposit(userId, request.getAmount());
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(@RequestBody WalletTransactionRequest request, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return walletService.withdraw(userId, request.getAmount());
    }
    @GetMapping("/")
    public Wallet getWallet(@AuthenticationPrincipal Jwt jwt) {
        return walletService.getorCreateWallet(jwt.getSubject());
    }
}