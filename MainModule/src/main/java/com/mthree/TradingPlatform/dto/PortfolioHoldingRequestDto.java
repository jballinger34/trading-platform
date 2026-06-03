package com.mthree.TradingPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioHoldingRequestDto {

    private String userId;
    private String symbol;
    private Integer quantity;
    private Double averagePrice;
}