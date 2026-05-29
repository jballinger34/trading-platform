package com.mthree.TradingPlatform.controller;

import com.mthree.TradingPlatform.dto.UserRequestDto;
import com.mthree.TradingPlatform.dto.UserResponseDto;

import com.mthree.TradingPlatform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {

        UserResponseDto response = userService.createUser(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
            @Valid @RequestBody UserRequestDto dto) {

        return userService.updateUser(id, dto);
    }
}