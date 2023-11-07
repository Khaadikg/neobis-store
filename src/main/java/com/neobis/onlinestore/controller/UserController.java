package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.dto.response.UserResponse;
import com.neobis.onlinestore.entity.UserInfo;
import com.neobis.onlinestore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User controller", description = "Uses for logic upon users")
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get all users", description = "Getting all users information")
    public List<UserResponse> getAllUsers() {
        return userService.getALlUsers();
    }

    @GetMapping("/info")
    @Operation(summary = "Update", description = "Updating user information by id")
    public UserResponse getPersonalInfo() {
        return userService.getUserPersonalInfo();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Save admin user", description = "Saving new admin user")
    public ResponseEntity<String> saveAdminUser(@Valid @RequestBody UserRequest request) {
        return userService.saveAdminUser(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get by id", description = "Getting user by id")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/info")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update", description = "Updating user information by id")
    public ResponseEntity<String> updateUserInfo(@Valid @RequestBody UserInfo info) {
        return userService.updateUserInfo(info);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(summary = "Update", description = "Updating user main info")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.updateUserMainInfo(userRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deleting user by id")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }

}
