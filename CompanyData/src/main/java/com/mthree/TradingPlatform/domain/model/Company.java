package com.mthree.TradingPlatform.domain.model;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
public class Company {
    @Id
    private String symbol;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Column(length = 100)
    private String sector;

    @Column(length = 100)
    private String industry;


    public static Company from(CompanyProfileDto dto){
        Company company = new Company();
        company.symbol = dto.symbol();
        company.name = dto.name();
        company.sector = dto.sector();
        company.industry = dto.industry();
        return company;
    }

}
