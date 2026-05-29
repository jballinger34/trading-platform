package com.mthree.TradingPlatform.entity;

import jakarta.persistence.*;
import lombok.*;
import com.mthree.TradingPlatform.enums.OAuthProvider;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    private LocalDateTime createdAt;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oauthProvider;

    private String oauthId;
}
