package by.yurnerix.service;

import by.yurnerix.dto.BookingResponse;
import by.yurnerix.dto.CreateBookingRequest;
import by.yurnerix.enums.BookingStatus;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request, String userEmail);
    BookingResponse getBookingById(Long bookingId);
    List<BookingResponse> getAllBookings();
    List<BookingResponse> getBookingsForUser(String userEmail);
    void cancelBooking(Long bookingId, String userEmail, boolean isAdmin);
    void updateBookingStatus(Long bookingId, BookingStatus status, String userEmail, boolean isAdmin);
}