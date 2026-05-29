package com.mthree.TradingPlatform.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UserResponseDto {

    private UUID id;

    private String username;

    private String email;

    private String name;

    private LocalDateTime createdAt;
}