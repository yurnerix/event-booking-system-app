package by.yurnerix.service;

import by.yurnerix.dto.AuditLogResponse;
import by.yurnerix.enums.AuditActionType;

import java.util.List;

public interface AuditLogService {

    void log(AuditActionType actionType,
             Long userId,
             Long bookingId,
             Long resourceId,
             String details);

    List<AuditLogResponse> getAllLogs();

    void clearAllLogs();
}