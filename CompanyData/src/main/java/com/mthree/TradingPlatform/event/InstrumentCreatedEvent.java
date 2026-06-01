package com.mthree.TradingPlatform.event;

import java.util.UUID;

public record InstrumentCreatedEvent (UUID instrumentId, String symbol, String exchange) {
}
