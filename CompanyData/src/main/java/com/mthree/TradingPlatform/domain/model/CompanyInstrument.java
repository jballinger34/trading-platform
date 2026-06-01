package com.mthree.TradingPlatform.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class CompanyInstrument {

    @Id
    private final UUID instrumentId;
    private final UUID companyId;
    private final String symbol;


    public CompanyInstrument(UUID instrumentId, UUID companyId, String symbol) {
        this.instrumentId = instrumentId;
        this.companyId = companyId;
        this.symbol = symbol;
    }
}