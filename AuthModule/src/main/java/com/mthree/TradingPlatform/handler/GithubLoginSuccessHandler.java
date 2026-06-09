package com.mthree.TradingPlatform.handler;

import com.mthree.TradingPlatform.client.UserServiceClient;
import com.mthree.TradingPlatform.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class GithubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public GithubLoginSuccessHandler(UserServiceClient userServiceClient, JwtService jwtService) {
        this.userServiceClient = userServiceClient;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String githubId = oauthUser.getAttribute("id").toString();
        String login = oauthUser.getAttribute("login").toString();

        UUID userId = userServiceClient.findOrCreateGithubUser(githubId, login);

        String jwt = jwtService.generateToken(userId);

        Cookie cookie = new Cookie("access_token", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3500);

        response.addCookie(cookie);
        response.sendRedirect("http://localhost:3000");
    }
}
