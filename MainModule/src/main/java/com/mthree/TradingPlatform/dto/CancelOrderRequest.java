package com.mthree.TradingPlatform.dto;

import java.util.UUID;

public record CancelOrderRequest(
        UUID userId,
        String symbol,
        UUID orderId
) {}
