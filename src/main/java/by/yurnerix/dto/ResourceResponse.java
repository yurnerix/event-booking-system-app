package by.yurnerix.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import by.yurnerix.enums.ResourceType;

import java.time.LocalDateTime;

public class ResourceResponse {

    private Long id;
    private String name;
    private String description;
    private ResourceType type;
    private String location;
    private Integer capacity;
    private Boolean active;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime availableFrom;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime availableTo;

    public ResourceResponse() {
    }

    public ResourceResponse(Long id, String name, String description, ResourceType type, String location, Integer capacity, Boolean active, LocalDateTime availableFrom, LocalDateTime availableTo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.capacity = capacity;
        this.active = active;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ResourceType getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    public LocalDateTime getAvailableTo() {
        return availableTo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public void setAvailableTo(LocalDateTime availableTo) {
        this.availableTo = availableTo;
    }
}