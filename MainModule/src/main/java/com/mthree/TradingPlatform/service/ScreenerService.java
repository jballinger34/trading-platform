package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.client.CompanyDataClient;
import com.mthree.TradingPlatform.client.InstrumentClient;
import com.mthree.TradingPlatform.client.PriceClient;
import com.mthree.TradingPlatform.dto.*;
import com.mthree.TradingPlatform.request.StockScreenRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        List<PriceDto> prices = priceHistoryClient.getLatestPriceData(symbols);

        Map<String, CompanyProfileDto> profileMap = profiles.stream()
                .collect(Collectors.toMap(CompanyProfileDto::symbol, p->p));

        Map<String, FundamentalsDto> fundamentalsMap = fundamentals.stream()
                .collect(Collectors.toMap(FundamentalsDto::symbol, f->f));

        Map<String, PriceDto> priceMap = prices.stream()
                .collect(Collectors.toMap(PriceDto::symbol, p -> p));

        return symbols.stream()
                .map(symbol -> {

                    CompanyProfileDto profile = profileMap.get(symbol);
                    FundamentalsDto f = fundamentalsMap.get(symbol);
                    PriceDto price = priceMap.get(symbol);

                    // skip incomplete data
                    if (profile == null || f == null || price == null) {
                        return null;
                    }

                    return new ScreenedStockDto(
                            symbol,

                            profile.name(),
                            profile.sector(),
                            profile.industry(),

                            price.close(),
                            price.bucketStart(),
                            price.volume(),

                            f.marketCap(),
                            f.revenue(),
                            f.netIncome(),
                            f.eps(),

                            f.peRatio(),
                            f.roe(),
                            f.debtToEquity()
                    );
                })
                .filter(Objects::nonNull)
                .filter(stock -> matches(request, stock))
                .toList();

    }
    private boolean matches(StockScreenRequest r, ScreenedStockDto s) {
        if (r.sector() != null && !r.sector().equalsIgnoreCase(s.sector())) return false;
        if (r.industry() != null && !r.industry().equalsIgnoreCase(s.industry())) return false;

        if (r.minPrice() != null && s.price().compareTo(r.minPrice()) < 0) return false;
        if (r.maxPrice() != null && s.price().compareTo(r.maxPrice()) > 0) return false;
        if (r.minVolume() != null && BigDecimal.valueOf(s.volume()).compareTo(r.minVolume()) < 0) return false;
        if (r.maxVolume() != null && BigDecimal.valueOf(s.volume()).compareTo(r.maxVolume()) > 0) return false;

        if (r.minRevenue() != null && s.revenue().compareTo(r.minRevenue()) < 0) return false;
        if (r.maxRevenue() != null && s.revenue().compareTo(r.maxRevenue()) > 0) return false;
        if (r.minNetIncome() != null && s.netIncome().compareTo(r.minNetIncome()) < 0) return false;
        if (r.maxNetIncome() != null && s.netIncome().compareTo(r.maxNetIncome()) > 0) return false;
        if (r.minEps() != null && s.eps().compareTo(r.minEps()) < 0) return false;
        if (r.maxEps() != null && s.eps().compareTo(r.maxEps()) > 0) return false;

        if (r.minPe() != null && s.peRatio().compareTo(r.minPe()) < 0) return false;
        if (r.maxPe() != null && s.peRatio().compareTo(r.maxPe()) > 0) return false;

        if (r.minRoe() != null && s.roe().compareTo(r.minRoe()) < 0) return false;
        if (r.maxRoe() != null && s.roe().compareTo(r.maxRoe()) > 0) return false;
        if (r.minDebtToEquity() != null && s.debtToEquity().compareTo(r.minDebtToEquity()) < 0) return false;
        if (r.maxDebtToEquity() != null && s.debtToEquity().compareTo(r.maxDebtToEquity()) > 0) return false;
        if (r.minMarketCap() != null && s.marketCap().compareTo(BigDecimal.valueOf(r.minMarketCap())) < 0) return false;
        if (r.maxMarketCap() != null && s.marketCap().compareTo(BigDecimal.valueOf(r.maxMarketCap())) > 0) return false;


        return true;
    }

}
