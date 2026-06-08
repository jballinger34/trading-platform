package com.mthree.TradingPlatform.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class PortfolioClient {

    private final RestClient restClient;

    public PortfolioClient(@Qualifier("portfolioRestClient") RestClient restClient){
        this.restClient = restClient;
    }

    public Integer getHoldingQuantity(UUID userId, String symbol){
        if(userId == null || symbol == null) return 0;
        //send off to portfolio service endpoint to get amount, then return it.
        return restClient.get().uri(uriBuilder -> uriBuilder
                .path("/api/v1/portfolio/quantity")
                .queryParam("userId", userId)
                .queryParam("symbol", symbol)
                .build()
        ).retrieve().body(Integer.class);

    }


}
