package com.mthree.TradingPlatform.dto;

import java.util.List;

public record ScreenResultDto(
        List<ScreenedStockDto> stocks,
        long totalResults,
        int page,
        int size,
        int totalPages
) {}
