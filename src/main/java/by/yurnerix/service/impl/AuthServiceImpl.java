package by.yurnerix.service.impl;

import by.yurnerix.dto.AuthResponse;
import by.yurnerix.dto.LoginRequest;
import by.yurnerix.dto.RegisterRequest;
import by.yurnerix.entity.Role;
import by.yurnerix.entity.User;
import by.yurnerix.enums.AuditActionType;
import by.yurnerix.enums.RoleType;
import by.yurnerix.exception.DuplicateEmailException;
import by.yurnerix.exception.DuplicateUsernameException;
import by.yurnerix.repository.RoleRepository;
import by.yurnerix.repository.UserRepository;
import by.yurnerix.service.AuditLogService;
import by.yurnerix.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuditLogService auditLogService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AuditLogService auditLogService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.auditLogService = auditLogService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateEmailException("Пользователь с таким email уже существует");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DuplicateUsernameException("Пользователь с таким именем уже существует");
        }

        // ищем роль ROLE_USER в базе
        // если роли ещё нет, создаём её
        Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(RoleType.ROLE_USER);
                    return roleRepository.save(role);
                });

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());

        // Пароль сохраняем в зашифрованном виде через BCrypt
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        auditLogService.log(
                AuditActionType.USER_REGISTERED,
                savedUser.getId(),
                null,
                null,
                "Зарегистрирован новый пользователь: " + savedUser.getUsername()
        );

        return new AuthResponse(
                null,
                "Регистрация выполнена успешно",
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // при использовании Spring Security этот метод обычно уже не нужен
        // для самой аутентификации, потому что входом занимается Security
        // я его оставил в проекте как отдельный сервис

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь с таким email не найден"));

        // Проверяем пароль через PasswordEncoder
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("Пользователь отключён");
        }

        return new AuthResponse(
                null,
                "Вход выполнен успешно",
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}