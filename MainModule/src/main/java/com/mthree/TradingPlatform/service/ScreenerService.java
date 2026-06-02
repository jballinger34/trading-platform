package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.client.CompanyDataClient;
import com.mthree.TradingPlatform.client.InstrumentClient;
import com.mthree.TradingPlatform.client.PriceClient;
import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;
import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
import com.mthree.TradingPlatform.dto.ScreenedStockDto;
import com.mthree.TradingPlatform.request.StockScreenRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScreenerService {

    private final InstrumentClient instrumentClient;
    private final CompanyDataClient companyDataClient;
    private final PriceClient priceHistoryClient;

    public ScreenerService(InstrumentClient instrumentClient, CompanyDataClient companyDataClient, PriceClient priceHistoryClient) {
        this.instrumentClient = instrumentClient;
        this.companyDataClient = companyDataClient;
        this.priceHistoryClient = priceHistoryClient;
    }

    public List<ScreenedStockDto> screen(StockScreenRequest request){
        //get all symbols
        List<String> symbols = instrumentClient.getAllInstruments().stream().map(InstrumentResponseDto::symbol).toList();

        List<CompanyProfileDto> profiles = companyDataClient.getProfiles(symbols);
        List<FundamentalsDto> fundamentals = companyDataClient.getFundamentals(symbols);

        Map<String, CompanyProfileDto> profileMap = profiles.stream()
                .collect(Collectors.toMap(CompanyProfileDto::symbol, p->p));

        Map<String, FundamentalsDto> fundamentalsMap = fundamentals.stream()
                .collect(Collectors.toMap(FundamentalsDto::symbol, f->f));

        // still not fully implemented, need to fetch price then aggregate data as a StockScreenRequest

    }


}
