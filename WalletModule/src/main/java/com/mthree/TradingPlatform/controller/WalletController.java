package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.entity.Wallet;
import com.mthree.TradingPlatform.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/deposit")
    public Wallet deposit(@RequestParam double amount, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return walletService.deposit(userId, amount);
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(@RequestParam double amount, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return walletService.withdraw(userId, amount);
    }
    @GetMapping
    public Wallet getWallet(@AuthenticationPrincipal Jwt jwt) {
        return walletService.getorCreateWallet(jwt.getSubject());
    }
}