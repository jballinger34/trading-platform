package com.mthree.TradingPlatform.client;

import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class InstrumentClient {

    private final RestClient restClient;

    public InstrumentClient(@Qualifier("instrumentRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public List<InstrumentResponseDto> getAllInstruments(){
        return restClient.get().uri("api/v1/instruments").retrieve().body(new ParameterizedTypeReference<>() {});
    }

}
