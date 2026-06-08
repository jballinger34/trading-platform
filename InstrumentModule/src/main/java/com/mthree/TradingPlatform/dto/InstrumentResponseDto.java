package com.mthree.TradingPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstrumentResponseDto {

    private Long id;
    private String symbol;
    private String exchange;
}