package com.mthree.TradingPlatform.domain.model;

import com.mthree.TradingPlatform.dto.FundamentalsDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Fundamentals {

    @Id
    private UUID companyId;

    private BigDecimal revenue;

    private BigDecimal netIncome;

    private BigDecimal eps;

    private BigDecimal peRatio;

    private BigDecimal roe;

    private BigDecimal debtToEquity;

    private BigDecimal marketCap;

    public static Fundamentals from(UUID companyId, FundamentalsDto dto){
        Fundamentals fundamentals = new Fundamentals();
        fundamentals.companyId = companyId;

        fundamentals.revenue = dto.revenue();
        fundamentals.netIncome = dto.netIncome();
        fundamentals.eps = dto.eps();
        fundamentals.peRatio = dto.peRatio();
        fundamentals.roe = dto.roe();
        fundamentals.debtToEquity = dto.debtToEquity();
        fundamentals.marketCap = dto.marketCap();

        return fundamentals;
    }
}