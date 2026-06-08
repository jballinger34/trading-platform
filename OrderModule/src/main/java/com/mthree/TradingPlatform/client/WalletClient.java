package com.mthree.TradingPlatform.client;

import org.springframework.beans.factory.annotation.Qualifier;
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

    //TODO
    // make sure this endpoint is implemented

    public BigDecimal getFunds(UUID userId){
        if(userId == null) return new BigDecimal("0");
        //send off to wallet service endpoint to get amount, then return it.
        return restClient.get().uri(uriBuilder -> uriBuilder
                .path("/api/v1/wallet/available-funds")
                .queryParam("userId", userId)
                .build()
        ).retrieve().body(BigDecimal.class);
    }






}
