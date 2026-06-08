package com.mthree.TradingPlatform.repository;

import com.mthree.TradingPlatform.entity.User;
import com.mthree.TradingPlatform.enums.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findByOauthIdAndOauthProvider(String oauthId, OAuthProvider oauthProvider);

}