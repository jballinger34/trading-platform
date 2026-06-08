package com.mthree.TradingPlatform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstrumentRequestDto {

    private String symbol;
    private String exchange;
}