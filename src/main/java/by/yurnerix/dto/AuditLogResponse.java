package by.yurnerix.dto;

import by.yurnerix.enums.AuditActionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AuditLogResponse {

    private String id;
    private AuditActionType actionType;
    private Long userId;
    private Long bookingId;
    private Long resourceId;
    private String details;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime timestamp;

    public AuditLogResponse() {
    }

    public AuditLogResponse(String id, AuditActionType actionType, Long userId, Long bookingId, Long resourceId, String details, LocalDateTime timestamp) {
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

    public void setId(String id) {
        this.id = id;
    }

    public AuditActionType getActionType() {
        return actionType;
    }

    public void setActionType(AuditActionType actionType) {
        this.actionType = actionType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}