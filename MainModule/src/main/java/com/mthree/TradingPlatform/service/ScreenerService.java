package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.client.CompanyDataClient;
import com.mthree.TradingPlatform.client.InstrumentClient;
import com.mthree.TradingPlatform.client.PriceClient;
import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
import com.mthree.TradingPlatform.dto.ScreenedStockDto;
import com.mthree.TradingPlatform.request.StockScreenRequest;

import java.util.List;

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


    }


}
