package by.yurnerix.dto;

public class ApiMessageResponse {

    private String message; // текстовое сообщение от api

    public ApiMessageResponse() {
    }

    public ApiMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}