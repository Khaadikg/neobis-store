package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.LoginRequest;
import com.neobis.onlinestore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public String registration(@RequestBody LoginRequest request) {
        return authService.registration(request);
    }

}
