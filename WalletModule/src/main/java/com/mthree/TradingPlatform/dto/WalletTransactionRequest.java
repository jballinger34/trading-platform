package com.mthree.TradingPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletTransactionRequest {

    private String userId;
    private Double amount;
}