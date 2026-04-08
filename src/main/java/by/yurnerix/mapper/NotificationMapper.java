package by.yurnerix.mapper;

import by.yurnerix.document.NotificationDocument;
import by.yurnerix.dto.NotificationResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(NotificationDocument document) {
        if (document == null) {
            return null;
        }

        return new NotificationResponse(
                document.getId(),
                document.getUserId(),
                document.getType(),
                document.getMessage(),
                document.isRead(),
                document.getCreatedAt()
        );
    }
}