package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.client.CompanyDataClient;
import com.mthree.TradingPlatform.client.InstrumentClient;
import com.mthree.TradingPlatform.client.PriceClient;
import com.mthree.TradingPlatform.dto.*;
import com.mthree.TradingPlatform.request.StockScreenRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScreenerService {

    private final InstrumentClient instrumentClient;
    private final CompanyDataClient companyDataClient;
    private final PriceClient priceHistoryClient;

    public ScreenerService(InstrumentClient instrumentClient, CompanyDataClient companyDataClient, PriceClient priceHistoryClient) {
        this.instrumentClient = instrumentClient;
        this.companyDataClient = companyDataClient;
        this.priceHistoryClient = priceHistoryClient;
    }

    public ScreenResultDto screen(StockScreenRequest request){
        List<String> symbols = instrumentClient.getAllInstruments().stream().map(InstrumentResponseDto::symbol).toList();

        Map<String, CompanyProfileDto> profileMap = getProfileMap(symbols);
        Map<String, FundamentalsDto> fundamentalsMap = getFundamentalsMap(symbols);
        Map<String, PriceDto> priceMap = getPricesMap(symbols);

        Comparator<ScreenedStockDto> comparator = buildComparator(request);

        int page = request.page() == null ? 0 : request.page();
        int size = request.size() == null ? 50 : request.size();

        List<ScreenedStockDto> filtered = symbols.stream()
                .map(symbol -> {

                    CompanyProfileDto profile = profileMap.get(symbol);
                    FundamentalsDto f = fundamentalsMap.get(symbol);
                    PriceDto price = priceMap.get(symbol);

                    return buildStock(symbol, profile, f, price);
                })
                .filter(stock -> matches(request, stock))
                .toList();

        int totalResults = filtered.size();
        int totalPages = (int) Math.ceil((double) totalResults / size);

        List<ScreenedStockDto> pagedResults = filtered.stream()
                .sorted(comparator)
                .skip((long) page * size)
                .limit(size)
                .toList();
        return new ScreenResultDto(pagedResults, totalResults, page, size, totalPages);

    }
    private boolean matches(StockScreenRequest r, ScreenedStockDto s) {

        if (r.sector() != null && !r.sector().equalsIgnoreCase(s.sector())) return false;

        if (r.industry() != null && !r.industry().equalsIgnoreCase(s.industry())) return false;

        if(notInRange(s.price(), r.minPrice(), r.maxPrice())) return false;
        if(notInRange(s.volume(), r.minVolume(), r.maxVolume())) return false;

        if(notInRange(s.revenue(), r.minRevenue(), r.maxRevenue())) return false;
        if(notInRange(s.netIncome(), r.minNetIncome(), r.maxNetIncome())) return false;
        if(notInRange(s.eps(), r.minEps(), r.maxEps())) return false;
        if(notInRange(s.peRatio(), r.minPe(), r.maxPe())) return false;

        if(notInRange(s.roe(), r.minRoe(), r.maxRoe())) return false;
        if(notInRange(s.debtToEquity(), r.minDebtToEquity(), r.maxDebtToEquity())) return false;
        if(notInRange(s.marketCap(), r.minMarketCap(), r.maxMarketCap())) return false;

        return true;
    }

    private ScreenedStockDto buildStock(
            String symbol,
            CompanyProfileDto profile,
            FundamentalsDto fundamentals,
            PriceDto price) {

        return new ScreenedStockDto(
                symbol,

                profile == null ? null : profile.name(),
                profile == null ? null : profile.sector(),
                profile == null ? null :profile.industry(),

                price == null ? null : price.close(),
                price == null ? null : price.bucketStart(),
                price == null ? null : price.volume(),

                fundamentals == null ? null : fundamentals.marketCap(),
                fundamentals == null ? null : fundamentals.revenue(),
                fundamentals == null ? null : fundamentals.netIncome(),
                fundamentals == null ? null : fundamentals.eps(),

                fundamentals == null ? null : fundamentals.peRatio(),
                fundamentals == null ? null : fundamentals.roe(),
                fundamentals == null ? null : fundamentals.debtToEquity()
        );
    }

    private Comparator<ScreenedStockDto> buildComparator(StockScreenRequest request){
        String sortBy = request.sortBy() == null ? "marketCap" : request.sortBy();
        Comparator<ScreenedStockDto> comparator =
                switch (sortBy) {
                    case "price" -> Comparator.comparing(ScreenedStockDto::price, Comparator.nullsLast(Comparator.naturalOrder()));
                    case "volume" -> Comparator.comparingLong(ScreenedStockDto::volume);
                    case "marketCap" -> Comparator.comparing(ScreenedStockDto::marketCap, Comparator.nullsLast(Comparator.naturalOrder()));
                    case "pe" -> Comparator.comparing(ScreenedStockDto::peRatio, Comparator.nullsLast(Comparator.naturalOrder()));
                    case "roe" -> Comparator.comparing(ScreenedStockDto::roe, Comparator.nullsLast(Comparator.naturalOrder()));
                    default -> Comparator.comparing(ScreenedStockDto::marketCap, Comparator.nullsLast(Comparator.naturalOrder()));
                };

        if ("desc".equalsIgnoreCase(request.direction())) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
    private Map<String, CompanyProfileDto> getProfileMap(List<String> symbols){
        List<CompanyProfileDto> profiles = companyDataClient.getProfiles(symbols);
        return profiles.stream().collect(Collectors.toMap(CompanyProfileDto::symbol, p->p));
    }
    private Map<String, FundamentalsDto> getFundamentalsMap(List<String> symbols){
        List<FundamentalsDto> fundamentals = companyDataClient.getFundamentals(symbols);
        return fundamentals.stream().collect(Collectors.toMap(FundamentalsDto::symbol, f->f));
    }
    private Map<String, PriceDto> getPricesMap(List<String> symbols){
        List<PriceDto> prices = priceHistoryClient.getLatestPriceData(symbols);
        return prices.stream().collect(Collectors.toMap(PriceDto::symbol, p -> p));
    }
    private boolean notInRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (value == null) {
            return false;
        }

        if (min != null && value.compareTo(min) < 0) {
            return true;
        }

        return max != null && value.compareTo(max) > 0;
    }
    private boolean notInRange (Long value, Long min, Long max) {
        if(value == null) return false;
        if(min != null && value < min) return true;
        if(max != null && value > max) return true;
        return false;
    }

}
