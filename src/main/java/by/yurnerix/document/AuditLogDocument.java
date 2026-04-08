package by.yurnerix.document;

import by.yurnerix.enums.AuditActionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "audit_logs")
public class AuditLogDocument {

    @Id
    private String id;

    private AuditActionType actionType;
    private Long userId;
    private Long bookingId;
    private Long resourceId;
    private String details;
    private LocalDateTime timestamp;

    public AuditLogDocument() {
    }

    public AuditLogDocument(String id, AuditActionType actionType, Long userId, Long bookingId, Long resourceId, String details, LocalDateTime timestamp) {
        this.id = id;
        this.actionType = actionType;
        this.userId = userId;
        this.bookingId = bookingId;
        this.resourceId = resourceId;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public AuditActionType getActionType() {
        return actionType;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActionType(AuditActionType actionType) {
        this.actionType = actionType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}