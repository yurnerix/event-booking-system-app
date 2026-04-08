package by.yurnerix.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ApiController {
    @GetMapping("/api")
    public Map<String, String> apiInfo() {
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("resources", "/api/resources");
        endpoints.put("bookings", "/api/bookings");
        endpoints.put("notifications", "/api/notifications");
        endpoints.put("auditLogs", "/api/audit-logs");
        return endpoints;
    }

}
