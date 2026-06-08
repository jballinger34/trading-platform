package com.mthree.TradingPlatform.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWalletRequest {

    private String userId;
    private Double balance;
}
