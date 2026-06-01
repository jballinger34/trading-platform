package com.mthree.TradingPlatform.provider;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;

import java.util.List;

public interface DataProvider {

    CompanyProfileDto getCompanyProfile(String symbol);
    FundamentalsDto getFundamentals(String symbol);



}
