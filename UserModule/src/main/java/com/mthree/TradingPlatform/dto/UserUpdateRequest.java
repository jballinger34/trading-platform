package com.mthree.TradingPlatform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.mthree.TradingPlatform.enums.OAuthProvider;
@Getter
@Setter
public class UserUpdateRequest {

    private String username;
    private String email;
    private String name;
}