package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.model.User;
import com.neobis.onlinestore.model.UserInfo;
import com.neobis.onlinestore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User controller", description = "Uses for logic upon users")
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Getting all users information")
    public List<User> getAllUsers() {
        return userService.getALlUsers();
    }

    @PostMapping
    @Operation(summary = "Save user", description = "Saving new user")
    public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Getting user by id")
    public UserInfo getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update", description = "Updating user information by id")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserInfo info,
                                             @PathVariable Long id) {
        return userService.updateUser(info, id);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete user", description = "Deleting user by id")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
