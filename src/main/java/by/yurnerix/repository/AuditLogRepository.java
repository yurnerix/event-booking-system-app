package by.yurnerix.repository;

import by.yurnerix.document.AuditLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogRepository extends MongoRepository<AuditLogDocument, String> {
    List<AuditLogDocument> findByUserId(Long userId);
    List<AuditLogDocument> findByBookingId(Long bookingId);

}