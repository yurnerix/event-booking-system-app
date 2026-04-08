package by.yurnerix.service;

import by.yurnerix.document.NotificationDocument;
import by.yurnerix.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getNotificationsByUser(Long userId);
    NotificationResponse markNotificationAsRead(String notificationId);
    NotificationResponse sendNotification(NotificationDocument notificationDocument);
    void clearNotificationsByUser(Long userId);
}