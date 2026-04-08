package by.yurnerix.api;

import by.yurnerix.dto.AuditLogResponse;
import by.yurnerix.service.AuditLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogRestController {

    private final AuditLogService auditLogService;

    public AuditLogRestController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLogResponse> getAllLogs() {
        return auditLogService.getAllLogs();
    }
}