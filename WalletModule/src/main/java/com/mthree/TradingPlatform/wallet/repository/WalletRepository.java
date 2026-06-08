package com.mthree.TradingPlatform.wallet.repository;

import com.mthree.TradingPlatform.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository
        extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(String userId);
}
