package by.yurnerix.dto;

import java.time.LocalDateTime;

public class CreateBookingRequest {

    private Long resourceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(Long resourceId, LocalDateTime startTime, LocalDateTime endTime, String purpose) {
        this.resourceId = resourceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}