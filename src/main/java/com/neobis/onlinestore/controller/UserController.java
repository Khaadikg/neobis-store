package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.model.User;
import com.neobis.onlinestore.model.UserInfo;
import com.neobis.onlinestore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getALlUsers();
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserInfo info,
                                             @PathVariable Long id) {
        return userService.updateUser(info, id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
