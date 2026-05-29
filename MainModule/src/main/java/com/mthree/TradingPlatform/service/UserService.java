package com.mthree.TradingPlatform.service;

import com.mthree.TradingPlatform.dto.UserRequestDto;
import com.mthree.TradingPlatform.entity.User;
import com.mthree.TradingPlatform.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.mthree.TradingPlatform.dto.UserResponseDto;
import java.util.List;
import java.util.UUID;
import com.mthree.TradingPlatform.exception.UserNotFoundException;
import com.mthree.TradingPlatform.exception.UserAlreadyExistsException;
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .name(dto.getName())
                .oauthProvider(dto.getOauthProvider())
                .oauthId(dto.getOauthId())
                .build();

        User savedUser = userRepository.save(user);

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

    public UserResponseDto updateUser(UUID id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setOauthProvider(dto.getOauthProvider());
        user.setOauthId(dto.getOauthId());

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