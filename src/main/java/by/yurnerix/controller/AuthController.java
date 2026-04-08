package by.yurnerix.controller;

import by.yurnerix.dto.LoginRequest;
import by.yurnerix.dto.RegisterRequest;
import by.yurnerix.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register-form")
    public String registerUser(@ModelAttribute RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return "redirect:/login-page";
    }

    @PostMapping("/login-form")
    public String loginUser(@ModelAttribute LoginRequest loginRequest) {
        authService.login(loginRequest);
        return "redirect:/";
    }
}