package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.dto.response.UserResponse;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.UserInfo;
import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public List<UserResponse> getALlUsers() {
        return userRepository.findAll().stream().map(this::mapUserToUserResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        User user = checkExistAndReturnUser(id);
        return mapUserToUserResponse(user);
    }

    public ResponseEntity<String> saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User with username \"" + user.getUsername() + "\" already exist!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User saved fine!");
    }

    public ResponseEntity<String> updateUserInfo(UserInfo info, Long id) {
        User user = checkExistAndReturnUser(id);
        user.setInfo(info);
        userRepository.save(user);
        return ResponseEntity.ok("User successfully saved!");
    }

    public ResponseEntity<String> updateUserMainInfo(UserRequest request, Long id) {
        User user = checkExistAndReturnUser(id);
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

}
