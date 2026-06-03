package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.client.CompanyDataClient;
import com.mthree.TradingPlatform.client.InstrumentClient;
import com.mthree.TradingPlatform.client.PriceClient;
import com.mthree.TradingPlatform.dto.*;
import com.mthree.TradingPlatform.request.StockScreenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreenerServiceTest {
    @Mock private InstrumentClient instrumentClient;
    @Mock private CompanyDataClient companyDataClient;
    @Mock private PriceClient priceClient;

    @InjectMocks
    private ScreenerService screenerService;


    @BeforeEach
    public void setUp(){
        when(instrumentClient.getAllInstruments())
                .thenReturn(List.of(aaplInstrument(), msftInstrument()));

        when(companyDataClient.getProfiles(anyList()))
                .thenReturn(List.of(aaplProfile(), msftProfile()));

        when(companyDataClient.getFundamentals(anyList()))
                .thenReturn(List.of(aaplFundamentals(), msftFundamentals()));

        when(priceClient.getLatestPriceData(anyList()))
                .thenReturn(List.of(aaplPrice(), msftPrice()));
    }

    @Test
    void shouldBuildScreenedStock() {
        StockScreenRequest request =
                new StockScreenRequest(
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        null, null,
                        0, 50,
                        "marketCap",
                        "desc"
                );

        ScreenResultDto result = screenerService.screen(request);

        assertEquals(2, result.totalResults());

        ScreenedStockDto stock = result.stocks().get(0);

        assertEquals("AAPL", stock.symbol());
        assertEquals("Technology", stock.sector());
        assertEquals(BigDecimal.valueOf(200), stock.price());
    }
    @Test
    void shouldFilterBySector() {
        StockScreenRequest request =
                new StockScreenRequest(
                        "Technology",
                        null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        0,50,
                        "marketCap",
                        "desc"
                );

        ScreenResultDto result = screenerService.screen(request);

        assertEquals(2, result.stocks().size());
    }

    @Test
    void shouldFilterBySectorEmpty() {
        StockScreenRequest request =
                new StockScreenRequest(
                        "Healthcare",
                        null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        0,50,
                        "marketCap",
                        "desc"
                );

        ScreenResultDto result = screenerService.screen(request);

        assertTrue(result.stocks().isEmpty());
    }

    @Test
    void shouldFilterByMinimumPrice() {

        StockScreenRequest request =
                new StockScreenRequest(
                        null,
                        null,
                        BigDecimal.valueOf(300),
                        null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        0,50,
                        "marketCap",
                        "desc"
                );

        ScreenResultDto result = screenerService.screen(request);

        assertEquals(1, result.stocks().size());
    }
    @Test
    void shouldSortByPriceDescending() {

        StockScreenRequest request =
                new StockScreenRequest(
                        null,
                        null,
                        null, null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        null,null,
                        0,50,
                        "price",
                        "desc"
                );

        ScreenResultDto result = screenerService.screen(request);

        ScreenedStockDto stock = result.stocks().get(0);
        assertEquals("MSFT", stock.symbol());
    }


    //
    // sample data
    //

    private InstrumentResponseDto aaplInstrument() {
        return new InstrumentResponseDto("AAPL", "NASDAQ");
    }
    private CompanyProfileDto aaplProfile() {
        return new CompanyProfileDto(
                "AAPL",
                "Apple",
                "Technology",
                "Consumer Electronics"
        );
    }
    private FundamentalsDto aaplFundamentals() {
        return new FundamentalsDto(
                "AAPL",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(25),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(3_000_000_000L)
        );
    }
    private PriceDto aaplPrice() {
        return new PriceDto(
                "AAPL",
                Instant.now(),
                BigDecimal.valueOf(200),
                1_000_000
        );
    }

    private InstrumentResponseDto msftInstrument() {
        return new InstrumentResponseDto("MSFT", "NASDAQ");
    }
    private CompanyProfileDto msftProfile() {
        return new CompanyProfileDto(
                "MSFT",
                "Microsoft",
                "Technology",
                "IT Services"
        );
    }
    private FundamentalsDto msftFundamentals() {
        return new FundamentalsDto(
                "MSFT",
                BigDecimal.valueOf(800),
                BigDecimal.valueOf(300),
                BigDecimal.valueOf(8),
                BigDecimal.valueOf(25),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(1_000_000_000L)
        );
    }
    private PriceDto msftPrice() {
        return new PriceDto(
                "MSFT",
                Instant.now(),
                BigDecimal.valueOf(300),
                500_000
        );
    }
}