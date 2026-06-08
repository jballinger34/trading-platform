package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.InstrumentRequestDto;
import com.mthree.TradingPlatform.dto.InstrumentResponseDto;
import com.mthree.TradingPlatform.service.InstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instruments")
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService instrumentService;

    @PostMapping
    public InstrumentResponseDto createInstrument(
            @RequestBody InstrumentRequestDto requestDto) {

        return instrumentService.createInstrument(requestDto);
    }

    @GetMapping
    public List<InstrumentResponseDto> getAllInstruments() {

        return instrumentService.getAllInstruments();
    }

    @GetMapping("/{id}")
    public InstrumentResponseDto getInstrumentById(@PathVariable Long id) {

        return instrumentService.getInstrumentById(id);
    }

    @PutMapping("/{id}")
    public InstrumentResponseDto updateInstrument(
            @PathVariable Long id,
            @RequestBody InstrumentRequestDto requestDto) {

        return instrumentService.updateInstrument(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteInstrument(@PathVariable Long id) {

        instrumentService.deleteInstrument(id);
    }
}