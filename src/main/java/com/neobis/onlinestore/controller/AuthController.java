package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.LoginRequest;
import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.dto.response.LoginResponse;
import com.neobis.onlinestore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public String registration(@RequestBody @Valid UserRequest request) {
        return authService.registration(request);
    }

    @PostMapping("/sign-in")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

}
