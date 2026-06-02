package com.mthree.TradingPlatform.client;

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

}
