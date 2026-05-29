package com.mthree.TradingPlatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.mthree.TradingPlatform.enums.OAuthProvider;
@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private String name;

    private OAuthProvider oauthProvider;

    private String oauthId;
}