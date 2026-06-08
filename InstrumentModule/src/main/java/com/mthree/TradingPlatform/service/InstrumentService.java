package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.dto.InstrumentRequestDto;
import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
import com.mthree.TradingPlatform.entity.Instrument;
import com.mthree.TradingPlatform.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public InstrumentResponseDto createInstrument(InstrumentRequestDto requestDto) {

        if (instrumentRepository.existsBySymbol(requestDto.getSymbol())) {
            throw new RuntimeException("Instrument symbol already exists");
        }

        Instrument instrument = new Instrument();
        instrument.setSymbol(requestDto.getSymbol());
        instrument.setExchange(requestDto.getExchange());

        Instrument saved = instrumentRepository.save(instrument);

        return mapToResponse(saved);
    }

    public List<InstrumentResponseDto> getAllInstruments() {

        return instrumentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InstrumentResponseDto getInstrumentById(Long id) {

        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instrument not found"));

        return mapToResponse(instrument);
    }

    public InstrumentResponseDto updateInstrument(Long id, InstrumentRequestDto requestDto) {

        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instrument not found"));

        instrument.setSymbol(requestDto.getSymbol());
        instrument.setExchange(requestDto.getExchange());

        Instrument updated = instrumentRepository.save(instrument);

        return mapToResponse(updated);
    }

    public void deleteInstrument(Long id) {

        instrumentRepository.deleteById(id);
    }

    private InstrumentResponseDto mapToResponse(Instrument instrument) {

        InstrumentResponseDto dto = new InstrumentResponseDto();

        dto.setId(instrument.getId());
        dto.setSymbol(instrument.getSymbol());
        dto.setExchange(instrument.getExchange());

        return dto;
    }
}
