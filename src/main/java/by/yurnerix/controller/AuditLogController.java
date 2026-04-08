package by.yurnerix.controller;

import by.yurnerix.dto.AuditLogResponse;
import by.yurnerix.service.AuditLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public String getAuditLogs(Model model) {
        List<AuditLogResponse> logs = auditLogService.getAllLogs();
        model.addAttribute("logs", logs);
        return "audit-logs";
    }

    @PostMapping("/clear")
    public String clearAuditLogs() {
        auditLogService.clearAllLogs();
        return "redirect:/audit-logs";
    }
}