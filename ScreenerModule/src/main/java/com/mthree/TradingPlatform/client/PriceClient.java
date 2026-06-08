package com.mthree.TradingPlatform.client;

import com.mthree.TradingPlatform.dto.PriceDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PriceClient {

    private final RestClient restClient;

    public PriceClient(@Qualifier("priceRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<PriceDto> getLatestPriceData(List<String> symbols) {
        return restClient.get().uri(
                uriBuilder -> uriBuilder
                        .path("/api/v1/price-history/latest")
                        .queryParam("symbols", String.join(",", symbols))
                        .build()
        ).retrieve().body(new ParameterizedTypeReference<>() {});
    }

}
