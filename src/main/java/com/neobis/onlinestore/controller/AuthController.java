package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public String registration(@RequestBody UserRequest request) {
        return authService.registration(request);
    }

}
