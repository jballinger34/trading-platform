package com.mthree.TradingPlatform.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class PortfolioClient {

    private final RestClient restClient;

    public PortfolioClient(@Qualifier("portfolioRestClient") RestClient restClient){
        this.restClient = restClient;
    }

    public Integer getHoldingQuantity(Jwt token, String symbol){
        //send off to portfolio service endpoint to get amount, then return it.
        return restClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/v1/portfolio/quantity")
                        .queryParam("symbol", symbol)
                        .build()
                )
                .header("Authorization", "Bearer "+ token.getTokenValue())
                .retrieve()
                .body(Integer.class);

    }


}
