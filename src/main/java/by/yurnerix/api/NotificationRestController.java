package by.yurnerix.api;

import by.yurnerix.dto.NotificationResponse;
import by.yurnerix.entity.User;
import by.yurnerix.repository.UserRepository;
import by.yurnerix.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationRestController(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<NotificationResponse> getMyNotifications(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return notificationService.getNotificationsByUser(user.getId());
    }
}