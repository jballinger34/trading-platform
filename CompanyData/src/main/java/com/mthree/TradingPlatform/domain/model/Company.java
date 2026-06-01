package com.mthree.TradingPlatform.domain.model;

import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;

    @Getter @Setter
    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Getter @Setter
    @Column(length = 100)
    private String sector;

    @Getter @Setter
    @Column(length = 100)
    private String industry;


    public static Company from(CompanyProfileDto dto){
        Company company = new Company();
        company.setName(dto.name());
        company.setSector(dto.sector());
        company.setIndustry(dto.industry());
        return company;
    }

}
