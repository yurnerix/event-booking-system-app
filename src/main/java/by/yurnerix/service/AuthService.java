package by.yurnerix.service;

import by.yurnerix.dto.AuthResponse;
import by.yurnerix.dto.LoginRequest;
import by.yurnerix.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}