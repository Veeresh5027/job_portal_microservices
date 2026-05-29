package com.vp.jobportal.controller;

import com.vp.job.dto.response.UserResponse;
import com.vp.jobportal.mapper.UserMapper; // Kept to call methods statically
import com.vp.jobportal.model.User;
import com.vp.jobportal.payload.UpdateUserRequest;
import com.vp.jobportal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(@RequestHeader("X-User-Email") String email) throws Exception {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestHeader("X-User-Email") String email,
            @RequestBody UpdateUserRequest req) throws Exception {
        return ResponseEntity.ok(userService.updateUser(email, req));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") Long userId) throws Exception {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(UserMapper.toDTOList(users));
    }

    @PatchMapping("/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(@PathVariable("userId") Long userId) throws Exception {
        return ResponseEntity.ok(userService.suspendUser(userId));
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable("userId") Long userId) throws Exception {
        return ResponseEntity.ok(userService.activateUser(userId));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("userId") Long userId) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}