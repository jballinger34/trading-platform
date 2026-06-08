package com.mthree.TradingPlatform.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {

    private int status;

    private String message;

    private LocalDateTime timestamp;
}