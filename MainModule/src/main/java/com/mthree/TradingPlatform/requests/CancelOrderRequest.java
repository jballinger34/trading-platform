package com.mthree.TradingPlatform.requests;

import java.util.UUID;

public record CancelOrderRequest(
        String symbol,
        UUID orderId
) {}
