package com.mthree.TradingPlatform.dto;

import com.mthree.TradingPlatform.domain.model.Company;

public record CompanyProfileDto(String symbol, String name, String sector, String industry) {

    public static CompanyProfileDto from(Company company){
        return new CompanyProfileDto(company.getSymbol(), company.getName(), company.getSector(), company.getIndustry());
    }

}
