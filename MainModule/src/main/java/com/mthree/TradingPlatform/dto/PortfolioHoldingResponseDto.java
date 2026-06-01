package com.mthree.TradingPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioHoldingResponseDto {

    private Long id;
    private String userId;
    private Long instrumentId;
    private Integer quantity;
    private Double averagePrice;
}