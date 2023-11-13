package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.LoginRequest;
import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.dto.response.LoginResponse;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.enums.Role;
import com.neobis.onlinestore.exception.IncorrectLoginException;
import com.neobis.onlinestore.exception.UserAlreadyExistException;
import com.neobis.onlinestore.repository.UserRepository;
import com.neobis.onlinestore.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public String registration(UserRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User with username = " + request.getUsername() + " already exist");
        }
        userRepository.save(mapLoginRequestToUser(request));
        return "User successfully registered!";
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User existUser = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IncorrectLoginException("Username is not correct = " + loginRequest.getUsername()));
        if (encoder.matches(loginRequest.getPassword(), existUser.getPassword())) {
            return loginView(jwtTokenUtil.generateToken(existUser), existUser);
        } else {
            throw new IncorrectLoginException("Password is not correct or Access denied! You are not registered");
        }
    }

    public LoginResponse loginView(String token, User user) {
        return LoginResponse.builder()
                .jwt(token)
                .username(user.getUsername())
                .authorities(user.getAuthorities().toString())
                .build();
    }

    public User mapLoginRequestToUser(UserRequest request) {
        return User.builder()
                .role(Role.USER)
                .mailing(request.isMailing())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build();
    }
}