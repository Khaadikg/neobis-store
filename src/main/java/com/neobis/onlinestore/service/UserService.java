package com.neobis.onlinestore.service;

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

    public List<User> getALlUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<String> saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Customer with username \"" + user.getUsername() + "\" already exist!");
        }
        userRepository.save(user);
        return ResponseEntity.ok("Customer saved fine!");
    }

    public ResponseEntity<String> updateUser(UserInfo info, Long id) {
        User user1 = userRepository.findById(id).orElse(null);
        if (user1 == null) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        user1.setInfo(info);
        userRepository.save(user1);
        return ResponseEntity.ok("Customer successfully saved!");
    }

    public ResponseEntity<String> deleteUserById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().body("No such user found by id = " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Customer successfully deleted!");
    }
}
