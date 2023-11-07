package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.LoginRequest;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.enums.Role;
import com.neobis.onlinestore.exception.UserAlreadyExistException;
import com.neobis.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    public String registration(LoginRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User with username = "+ request.getUsername()+" already exist");
        }
        userRepository.save(mapLoginRequestToUser(request));
        return "User successfully registered!";
    }


    public User mapLoginRequestToUser(LoginRequest request) {
        return User.builder()
                .role(Role.USER)
                .mailing(request.isMailing())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build();
    }
}
