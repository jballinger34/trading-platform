package com.mthree.TradingPlatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient portfolioRestClient(@Value("${services.portfolio.url}") String baseUrl){
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public RestClient walletRestClient(@Value("${services.wallet.url}") String baseUrl){
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }


}
