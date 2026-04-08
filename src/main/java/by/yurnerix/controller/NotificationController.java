package by.yurnerix.controller;

import by.yurnerix.dto.NotificationResponse;
import by.yurnerix.entity.User;
import by.yurnerix.repository.UserRepository;
import by.yurnerix.service.NotificationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getCurrentUserNotifications(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        List<NotificationResponse> notifications = notificationService.getNotificationsByUser(user.getId());
        model.addAttribute("notifications", notifications);
        return "notifications";
    }

    @PostMapping("/read/{notificationId}")
    public String markAsRead(@PathVariable String notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return "redirect:/notifications";
    }

    @PostMapping("/clear")
    public String clearMyNotifications(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        notificationService.clearNotificationsByUser(user.getId());
        return "redirect:/notifications";
    }
}