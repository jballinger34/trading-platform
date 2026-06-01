package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.domain.model.Company;
import com.mthree.TradingPlatform.domain.model.CompanyInstrument;
import com.mthree.TradingPlatform.domain.model.Fundamentals;
import com.mthree.TradingPlatform.dto.CompanyProfileDto;
import com.mthree.TradingPlatform.dto.FundamentalsDto;
import com.mthree.TradingPlatform.dto.ScreenerView;
import com.mthree.TradingPlatform.event.InstrumentCreatedEvent;
import com.mthree.TradingPlatform.provider.DataProvider;
import com.mthree.TradingPlatform.repo.CompanyRepository;
import com.mthree.TradingPlatform.repo.FundamentalsRepository;
import com.mthree.TradingPlatform.repo.MappingRepository;
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
    private final MappingRepository mappingRepository;

    public CompanyDataService(DataProvider dataProvider, CompanyRepository companyRepository,
                              FundamentalsRepository snapshotRepository, MappingRepository mappingRepository) {
        this.dataProvider = dataProvider;
        this.companyRepository = companyRepository;
        this.fundamentalsRepository = snapshotRepository;
        this.mappingRepository = mappingRepository;
    }

    public void ingestInstrument(InstrumentCreatedEvent event){
        //return if we already have an instrument with that ID stored
        //this method should only handle automatically getting data for new
        //instruments added in instrument service
        if(mappingRepository.existsById(event.instrumentId())) return;

        CompanyProfileDto profileDto = dataProvider.getCompanyProfile(event.symbol());
        Company company = Company.from(profileDto);
        final Company savedCompany = companyRepository.save(company);

        FundamentalsDto fundamentalsDto = dataProvider.getFundamentals(event.symbol());
        Fundamentals fundamentals = Fundamentals.from(savedCompany.getId(), fundamentalsDto);
        fundamentalsRepository.save(fundamentals);

        CompanyInstrument mapping = new CompanyInstrument(event.instrumentId(), savedCompany.getId(), event.symbol());
        mappingRepository.save(mapping);
    }

    public List<ScreenerView> getAllScreenerData(){

        List<CompanyInstrument> instrumentMappings = mappingRepository.findAll();
        Map<UUID, Company> companyMap = companyRepository.findAll().stream().collect(Collectors.toMap(Company::getId, c -> c));
        Map<UUID, Fundamentals> fundamentalsMap = fundamentalsRepository.findAll().stream().collect(Collectors.toMap(Fundamentals::getCompanyId, f->f));

        return instrumentMappings.stream()
                .map(instrument -> {
                    Company company = companyMap.get(instrument.getCompanyId());
                    if(company == null) throw new RuntimeException("Company not found for mapping");
                    CompanyProfileDto profileDto = CompanyProfileDto.from(company);

                    Fundamentals fundamentals = fundamentalsMap.get(company.getId());
                    if(fundamentals == null) throw new RuntimeException("Fundamentals not found for mapping");
                    FundamentalsDto fundamentalsDto = FundamentalsDto.from(fundamentals);

                    return new ScreenerView(instrument.getSymbol(), profileDto, fundamentalsDto);
                })
                .toList();
    }

}
