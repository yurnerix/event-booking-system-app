package by.yurnerix.service.impl;

import by.yurnerix.document.NotificationDocument;
import by.yurnerix.dto.NotificationResponse;
import by.yurnerix.repository.NotificationRepository;
import by.yurnerix.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationResponse> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(notification -> new NotificationResponse(
                        notification.getId(),
                        notification.getUserId(),
                        notification.getType(),
                        notification.getMessage(),
                        notification.isRead(),
                        notification.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponse markNotificationAsRead(String notificationId) {
        NotificationDocument notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Уведомление не найдено"));

        notification.setRead(true);
        notificationRepository.save(notification);

        return new NotificationResponse(
                notification.getId(),
                notification.getUserId(),
                notification.getType(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }

    @Override
    public NotificationResponse sendNotification(NotificationDocument notificationDocument) {
        NotificationDocument saved = notificationRepository.save(notificationDocument);

        return new NotificationResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getType(),
                saved.getMessage(),
                saved.isRead(),
                saved.getCreatedAt()
        );
    }

    @Override
    public void clearNotificationsByUser(Long userId) {
        List<NotificationDocument> notifications = notificationRepository.findByUserId(userId);
        notificationRepository.deleteAll(notifications);
    }
}