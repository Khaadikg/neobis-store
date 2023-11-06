package com.neobis.onlinestore.service;

import com.neobis.onlinestore.dto.request.LoginRequest;
import com.neobis.onlinestore.dto.response.LoginResponse;
import com.neobis.onlinestore.entity.User;
import com.neobis.onlinestore.entity.enums.Role;
import com.neobis.onlinestore.exception.IncorrectLoginException;
import com.neobis.onlinestore.exception.UserAlreadyExistException;
import com.neobis.onlinestore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public LoginResponse login(LoginRequest loginRequest) {
        User existUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                        () -> new IncorrectLoginException("Email is not correct"));
        if (encoder.matches(loginRequest.getPassword(), existUser.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(existUser, null,
                    AuthorityUtils.createAuthorityList("USER"));
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return loginMapper.loginView(jwtTokenUtil.generateToken(user), "SUCCESS", user);
            return null;
        } else {
            throw new IncorrectLoginException("Password is not correct" + " or " + "Access denied! You are not registered");
        }
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
