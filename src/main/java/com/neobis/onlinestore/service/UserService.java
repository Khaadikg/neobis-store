package com.neobis.onlinestore.service;

import com.neobis.onlinestore.exception.NotFoundException;
import com.neobis.onlinestore.model.User;
import com.neobis.onlinestore.model.UserInfo;
import com.neobis.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserInfo> getALlUsers() {
        return userRepository.findAll().stream().map(User::getInfo).toList();
    }

    public UserInfo getUserById(Long id) {
        User user = checkExistAndReturnUser(id);
        return user.getInfo();
    }

    public ResponseEntity<String> saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User with username \"" + user.getUsername() + "\" already exist!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User saved fine!");
    }

    public ResponseEntity<String> updateUser(UserInfo info, Long id) {
        User user = checkExistAndReturnUser(id);
        user.setInfo(info);
        userRepository.save(user);
        return ResponseEntity.ok("User successfully saved!");
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
