package com.neobis.onlinestore.controller;

import com.neobis.onlinestore.dto.request.UserRequest;
import com.neobis.onlinestore.service.AuthService;
import com.neobis.onlinestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MVCController {

    private final AuthService authService;
    private final UserService userService;
    @Autowired
    public MVCController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }
    @RequestMapping("/")
    public ResponseEntity<String> getMain() {
        return ResponseEntity.ok("HELLO WORLD!");
    }
    @RequestMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute(userService.getAuthenticatedUser());
        return "index";
    }

    @GetMapping("/registration")
    public String addUser(Model model){
        model.addAttribute("user", new UserRequest());
        return "/registration";
    }
    @GetMapping("/register")
    public String showRegistrationPage(@ModelAttribute("user") UserRequest request, Model model) {
        model.addAttribute("string", authService.registration(request));
        return "registration";
    }
}
