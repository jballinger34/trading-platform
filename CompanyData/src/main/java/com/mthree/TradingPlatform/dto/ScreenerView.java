package com.mthree.TradingPlatform.dto;

public record ScreenerView(
        String symbol,
        CompanyProfileDto companyProfileDto,
        FundamentalsDto fundamentalsDto
){}