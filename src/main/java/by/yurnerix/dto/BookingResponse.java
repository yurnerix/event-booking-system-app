package by.yurnerix.dto;

import by.yurnerix.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class BookingResponse {

    private Long id;
    private Long userId;
    private String username;
    private Long resourceId;
    private String resourceName;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime endTime;
    private BookingStatus status;
    private String purpose;

    public BookingResponse() {
    }

    public BookingResponse(Long id, Long userId, String username, Long resourceId, String resourceName, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status, String purpose) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.purpose = purpose;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}