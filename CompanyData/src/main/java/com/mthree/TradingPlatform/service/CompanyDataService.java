package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.CompanyDataApp;
import com.mthree.TradingPlatform.domain.model.Company;
import com.mthree.TradingPlatform.domain.model.Fundamentals;
import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;
import com.mthree.TradingPlatform.event.InstrumentCreatedEvent;
import com.mthree.TradingPlatform.provider.DataProvider;
import com.mthree.TradingPlatform.repo.CompanyRepository;
import com.mthree.TradingPlatform.repo.FundamentalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyDataService {

    private final DataProvider dataProvider;
    private final CompanyRepository companyRepository;
    private final FundamentalsRepository fundamentalsRepository;

    public CompanyDataService(DataProvider dataProvider, CompanyRepository companyRepository,
                              FundamentalsRepository snapshotRepository) {
        this.dataProvider = dataProvider;
        this.companyRepository = companyRepository;
        this.fundamentalsRepository = snapshotRepository;
    }

    public void ingestInstrument(InstrumentCreatedEvent event){
        //return if we already have an instrument with that ID stored
        //this method should only handle automatically getting data for new
        //instruments added in instrument service
        if(companyRepository.existsById(event.symbol())) return;

        CompanyProfileDto profileDto = dataProvider.getCompanyProfile(event.symbol());
        Company company = Company.from(profileDto);

        companyRepository.save(company);

        FundamentalsDto fundamentalsDto = dataProvider.getFundamentals(event.symbol());
        Fundamentals fundamentals = Fundamentals.from(event.symbol(), fundamentalsDto);

        fundamentalsRepository.save(fundamentals);
    }

    public CompanyProfileDto getProfileBySymbol(String symbol){
        Company company = companyRepository.findById(symbol).orElseThrow();
        return CompanyProfileDto.from(company);
    }
    public List<CompanyProfileDto> getProfilesBySymbols(List<String> symbols){
        List<Company> companies = companyRepository.findAllById(symbols);
        return companies.stream().map(CompanyProfileDto::from).toList();
    }
    public FundamentalsDto getFundamentalsBySymbol(String symbol){
        Fundamentals fundamentals = fundamentalsRepository.findById(symbol).orElseThrow();
        return FundamentalsDto.from(fundamentals);
    }
    public List<FundamentalsDto> getFundamentalsBySymbols(List<String> symbols){
        List<Fundamentals> allFundamentals = fundamentalsRepository.findAllById(symbols);
        return allFundamentals.stream().map(FundamentalsDto::from).toList();
    }



}
