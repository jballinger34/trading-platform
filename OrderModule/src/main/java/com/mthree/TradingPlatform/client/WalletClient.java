package com.mthree.TradingPlatform.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletClient {

    private final RestClient restClient;

    public WalletClient(@Qualifier("walletRestClient") RestClient restClient){
        this.restClient = restClient;
    }

    public BigDecimal getFunds(Jwt token){
        //send off to wallet service endpoint to get amount, then return it.
        return restClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/v1/wallet/available-funds")
                        .build())
                .header("Authorization", "Bearer "+ token.getTokenValue())
                .retrieve()
                .body(BigDecimal.class);
    }






}
