package com.mthree.TradingPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioSummaryDto {

    private Integer totalPositions;
    private Integer totalQuantity;
    private Double totalInvestment;
}