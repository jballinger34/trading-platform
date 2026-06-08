package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.dto.GithubUserRequest;
import com.mthree.TradingPlatform.dto.UserUpdateRequest;
import com.mthree.TradingPlatform.entity.User;
import com.mthree.TradingPlatform.enums.OAuthProvider;
import com.mthree.TradingPlatform.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.mthree.TradingPlatform.dto.UserResponseDto;
import java.util.List;
import java.util.UUID;
import com.mthree.TradingPlatform.exception.UserNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getOrCreateGithubUser(GithubUserRequest request){
        OAuthProvider provider = OAuthProvider.GITHUB;
        String oauthId = request.githubId();

        User savedUser = userRepository.findByOauthIdAndOauthProvider(oauthId, OAuthProvider.GITHUB)
                .orElseGet(() -> {
                    User user = User.builder()
                                    .oauthProvider(provider)
                                    .oauthId(oauthId)
                                    .username(request.username())
                                    .build();
                    userRepository.save(user);
                    return user;
                });
        return UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    public UserResponseDto getUserById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public void deleteUser(UUID id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }

    public UserResponseDto updateUser(UUID id, UserUpdateRequest dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(dto.getUsername() != null) user.setUsername(dto.getUsername());
        if(dto.getEmail() != null) user.setEmail(dto.getEmail());
        if(dto.getName() != null) user.setName(dto.getName());


        User updatedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .name(updatedUser.getName())
                .createdAt(updatedUser.getCreatedAt())
                .build();
    }

    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .name(user.getName())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
    }
}