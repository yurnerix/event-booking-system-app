package by.yurnerix.mapper;

import by.yurnerix.dto.BookingResponse;
import by.yurnerix.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getUser().getUsername(),
                booking.getResource().getId(),
                booking.getResource().getName(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus(),
                booking.getPurpose()
        );
    }
}