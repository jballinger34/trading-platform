package com.mthree.TradingPlatform.events;

import java.util.UUID;

public record UnreserveStockEvent (
        UUID userId,
        String symbol,
        Long quantity
){
}
