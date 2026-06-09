package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.GithubUserRequest;
import com.mthree.TradingPlatform.dto.UserUpdateRequest;
import com.mthree.TradingPlatform.dto.UserResponseDto;


import com.mthree.TradingPlatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/oauth/github")
    public ResponseEntity<UserResponseDto> getOrCreateGithubUser(@RequestBody GithubUserRequest request){
        UserResponseDto user = userService.getOrCreateGithubUser(request);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {

        UserResponseDto response = userService.getUserById(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest dto) {

        return userService.updateUser(id, dto);
    }
}