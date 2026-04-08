package by.yurnerix.service.impl;

import by.yurnerix.document.AuditLogDocument;
import by.yurnerix.dto.AuditLogResponse;
import by.yurnerix.enums.AuditActionType;
import by.yurnerix.repository.AuditLogRepository;
import by.yurnerix.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void log(AuditActionType actionType,
                    Long userId,
                    Long bookingId,
                    Long resourceId,
                    String details) {

        AuditLogDocument log = new AuditLogDocument();
        log.setActionType(actionType);
        log.setUserId(userId);
        log.setBookingId(bookingId);
        log.setResourceId(resourceId);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    @Override
    public List<AuditLogResponse> getAllLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(log -> new AuditLogResponse(
                        log.getId(),
                        log.getActionType(),
                        log.getUserId(),
                        log.getBookingId(),
                        log.getResourceId(),
                        log.getDetails(),
                        log.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void clearAllLogs() {
        auditLogRepository.deleteAll();
    }
}