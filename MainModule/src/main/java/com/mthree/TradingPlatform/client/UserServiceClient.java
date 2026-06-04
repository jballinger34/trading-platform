package com.mthree.TradingPlatform.client;

import com.mthree.TradingPlatform.dto.GithubUserRequest;
import com.mthree.TradingPlatform.dto.UserResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class UserServiceClient {

    private final RestClient restClient;


    public UserServiceClient(RestClient.Builder builder){
        //TODO CHANGE THIS TO URL OF USER SERVICE
        this.restClient = builder.baseUrl("http://localhost:8081").build();
    }
    public UUID findOrCreateGithubUser(String githubId, String username){
        UserResponse response = restClient.post()
                .uri("/users/oauth/github")
                .body(new GithubUserRequest(githubId, username))
                .retrieve()
                .body(UserResponse.class);

        return response.id();
    }


}
