package com.mthree.TradingPlatform.wallet.controller;

import com.mthree.TradingPlatform.wallet.dto.CreateWalletRequest;
import com.mthree.TradingPlatform.wallet.entity.Wallet;
import com.mthree.TradingPlatform.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}