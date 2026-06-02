package com.mthree.TradingPlatform.client;

import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
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


}
