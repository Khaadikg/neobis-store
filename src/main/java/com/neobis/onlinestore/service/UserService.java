package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.dto.response.UserResponse;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.UserInfo;
import com.neobis.onlinestore.entity.enums.Role;
import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public List<UserResponse> getALlUsers() {
        return userRepository.findAll().stream().map(this::mapUserToUserResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        User user = checkExistAndReturnUser(id);
        return mapUserToUserResponse(user);
    }

    public UserResponse getUserPersonalInfo() {
        User user = checkExistAndReturnUserByName(getAuthenticatedUsername());
        return mapUserToUserResponse(user);
    }

    public ResponseEntity<String> saveAdminUser(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Admin with username \"" + request.getUsername() + "\" already exist!");
        }
        userRepository.save(User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .mailing(false)
                .role(Role.ADMIN)
                .build());
        return ResponseEntity.ok("Admin saved fine!");
    }

    public ResponseEntity<String> updateUserInfo(UserInfo info) {
        User user = checkExistAndReturnUserByName(getAuthenticatedUsername());
        user.setInfo(info);
        userRepository.save(user);
        return ResponseEntity.ok("User successfully saved!");
    }

    public ResponseEntity<String> updateUserMainInfo(UserRequest request) {
        User user = checkExistAndReturnUserByName(getAuthenticatedUsername());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok("User successfully saved!");
    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .userInfo(user.getInfo())
                .build();
    }

    public ResponseEntity<String> deleteUserById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User successfully deleted!");
    }

    private User checkExistAndReturnUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No such user found by id = " + id)
        );
    }

    private User checkExistAndReturnUserByName(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new NotFoundException("No such user found by username = " + name)
        );
    }

    public String getAuthenticatedUsername() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            throw new NotFoundException("Authenticated user is null");
        }
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details.getUsername();
    }

}
