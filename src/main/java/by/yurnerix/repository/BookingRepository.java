package by.yurnerix.repository;

import by.yurnerix.entity.Booking;
import by.yurnerix.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
            SELECT b
            FROM Booking b
            WHERE b.resource.id = :resourceId
              AND b.status IN :statuses
              AND b.startTime < :endTime
              AND b.endTime > :startTime
            """)
    List<Booking> findConflictingBookings(Long resourceId, LocalDateTime startTime, LocalDateTime endTime, List<BookingStatus> statuses);

    List<Booking> findByUserId(Long userId);
}