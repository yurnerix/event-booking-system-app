package by.yurnerix.dto;

public class AuthResponse {

    private String token; // jwt-токен, вернётся после успешного логина или регистрации

    private String type; // тип токена

    private Long userId;

    private String username;

    private String email;


    public AuthResponse() {
    }

    public AuthResponse(String token, String type, Long userId, String username, String email) {
        this.token = token;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}