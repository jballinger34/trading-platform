package com.mthree.TradingPlatform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProcessedEvent {
    @Id
    private UUID eventId;

    private Instant processedAt;

}
