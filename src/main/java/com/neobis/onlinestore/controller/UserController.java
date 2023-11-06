package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.dto.response.UserResponse;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.UserInfo;
import com.neobis.onlinestore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User controller", description = "Uses for logic upon users")
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Getting all users information")
    public List<UserResponse> getAllUsers() {
        return userService.getALlUsers();
    }

    @PostMapping
    @Operation(summary = "Save user", description = "Saving new user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get by id", description = "Getting user by id")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/info/{id}")
    @Operation(summary = "Update", description = "Updating user information by id")
    public ResponseEntity<String> updateUserInfo(@Valid @RequestBody UserInfo info,
                                             @PathVariable Long id) {
        return userService.updateUserInfo(info, id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Updating user main info")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest userRequest,
                                             @PathVariable Long id) {
        return userService.updateUserMainInfo(userRequest, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deleting user by id")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
