package com.mthree.TradingPlatform.provider;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;


public interface DataProvider {

    CompanyProfileDto getCompanyProfile(String symbol);
    FundamentalsDto getFundamentals(String symbol);



}
