package com.mthree.TradingPlatform.provider;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDataProvider implements DataProvider {

    private final Map<String, CompanyProfileDto> companyMap = new HashMap<>();
    private final Map<String, FundamentalsDto> snapshotMap = new HashMap<>();

    public CsvDataProvider() {
        loadCompanies();
        loadSnapshots();
    }

    @Override
    public CompanyProfileDto getCompanyProfile(String symbol) {
        CompanyProfileDto dto = companyMap.get(symbol);
        if (dto == null) {
            throw new RuntimeException("No company found for symbol: " + symbol);
        }
        return dto;
    }

    @Override
    public FundamentalsDto getFundamentals(String symbol) {
        FundamentalsDto dto = snapshotMap.get(symbol);
        if (dto == null) {
            throw new RuntimeException("No fundamentals found for symbol: " + symbol);
        }
        return dto;
    }
    private void loadCompanies() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("company.csv").getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line = br.readLine(); // keep in - skips header

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                String symbol = p[0];

                companyMap.put(symbol, new CompanyProfileDto(
                        symbol,
                        p[1], // name
                        p[2], // sector
                        p[3]  // industry
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load company.csv", e);
        }
    }
    private void loadSnapshots() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("fundamentals.csv").getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line = br.readLine(); // keep in - skips header

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                String symbol = p[0];
                FundamentalsDto snapshot = new FundamentalsDto(
                        new BigDecimal(p[1]),
                        new BigDecimal(p[2]),
                        new BigDecimal(p[3]),
                        new BigDecimal(p[4]),
                        new BigDecimal(p[5]),
                        new BigDecimal(p[6]),
                        new BigDecimal(p[7])
                );

                snapshotMap.put(symbol, snapshot);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load fundamentals.csv", e);
        }
    }

}
