package com.mthree.TradingPlatform.client;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;
import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CompanyDataClient {

    private final RestClient restClient;

    public CompanyDataClient(@Qualifier("companyDataRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<FundamentalsDto> getFundamentals(List<String> symbols) {
        if(symbols == null || symbols.isEmpty()) return List.of();
        return restClient.get().uri(
                uriBuilder -> uriBuilder
                        .path("/api/v1/fundamentals")
                        .queryParam("symbols", String.join(",", symbols))
                        .build()
                ).retrieve().body(new ParameterizedTypeReference<>() {});
    }
    public List<CompanyProfileDto> getProfiles(List<String> symbols) {
        if(symbols == null || symbols.isEmpty()) return List.of();
        return restClient.get().uri(
                uriBuilder -> uriBuilder
                        .path("/api/v1/company-profile")
                        .queryParam("symbols", String.join(",", symbols))
                        .build()
        ).retrieve().body(new ParameterizedTypeReference<>() {});
    }

}
