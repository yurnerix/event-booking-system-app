package by.yurnerix.service.impl;

import by.yurnerix.document.NotificationDocument;
import by.yurnerix.dto.BookingResponse;
import by.yurnerix.dto.CreateBookingRequest;
import by.yurnerix.entity.Booking;
import by.yurnerix.entity.Resource;
import by.yurnerix.entity.User;
import by.yurnerix.enums.AuditActionType;
import by.yurnerix.enums.BookingStatus;
import by.yurnerix.enums.NotificationType;
import by.yurnerix.exception.BookingNotFoundException;
import by.yurnerix.exception.InvalidBookingTimeException;
import by.yurnerix.exception.ResourceNotFoundException;
import by.yurnerix.exception.SlotAlreadyBookedException;
import by.yurnerix.exception.UserNotFoundException;
import by.yurnerix.mapper.BookingMapper;
import by.yurnerix.repository.BookingRepository;
import by.yurnerix.repository.ResourceRepository;
import by.yurnerix.repository.UserRepository;
import by.yurnerix.service.AuditLogService;
import by.yurnerix.service.BookingService;
import by.yurnerix.service.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final AuditLogService auditLogService;
    private final NotificationService notificationService;

    public BookingServiceImpl(BookingRepository bookingRepository, ResourceRepository resourceRepository, UserRepository userRepository, BookingMapper bookingMapper, AuditLogService auditLogService, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
        this.auditLogService = auditLogService;
        this.notificationService = notificationService;
    }

    @Override
    public BookingResponse createBooking(CreateBookingRequest request, String userEmail) {
        Resource resource = resourceRepository.findById(request.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ресурс с id " + request.getResourceId() + " не найден"
                ));

        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().isEqual(request.getEndTime())) {
            throw new InvalidBookingTimeException(
                    "Время начала бронирования должно быть раньше времени окончания"
            );
        }

        if (request.getStartTime().isBefore(resource.getAvailableFrom()) || request.getEndTime().isAfter(resource.getAvailableTo())) {
            throw new InvalidBookingTimeException(
                    "Бронирование должно находиться в пределах доступности ресурса: c "
                            + resource.getAvailableFrom() + " до " + resource.getAvailableTo()
            );
        }

        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
                request.getResourceId(),
                request.getStartTime(),
                request.getEndTime(),
                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );

        if (!conflictingBookings.isEmpty()) {
            throw new SlotAlreadyBookedException("Выбранный временной слот уже занят");
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        "Пользователь с email " + userEmail + " не найден"
                ));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setResource(resource);
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPurpose(request.getPurpose());

        Booking savedBooking = bookingRepository.save(booking);

        auditLogService.log(
                AuditActionType.BOOKING_CREATED,
                user.getId(),
                savedBooking.getId(),
                resource.getId(),
                "Создано бронирование для ресурса: " + resource.getName()
        );

        NotificationDocument notification = new NotificationDocument();
        notification.setUserId(user.getId());
        notification.setType(NotificationType.BOOKING_CREATED);
        notification.setMessage("Ваше бронирование для ресурса \"" + resource.getName() + "\" успешно создано");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationService.sendNotification(notification);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Бронирование с id " + bookingId + " не найдено"
                ));

        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        "Пользователь с email " + userEmail + " не найден"
                ));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelBooking(Long bookingId, String userEmail, boolean isAdmin) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Бронирование с id " + bookingId + " не найдено"
                ));

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        "Пользователь с email " + userEmail + " не найден"
                ));

        if (!isAdmin && !booking.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Вы не можете удалить чужое бронирование");
        }

        auditLogService.log(
                AuditActionType.BOOKING_CANCELLED,
                currentUser.getId(),
                booking.getId(),
                booking.getResource().getId(),
                "Бронирование удалено"
        );

        NotificationDocument notification = new NotificationDocument();
        notification.setUserId(booking.getUser().getId());
        notification.setType(NotificationType.BOOKING_CANCELLED);
        notification.setMessage("Ваше бронирование для ресурса \"" + booking.getResource().getName() + "\" было удалено");
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationService.sendNotification(notification);

        bookingRepository.delete(booking);
    }

    @Override
    public void updateBookingStatus(Long bookingId, BookingStatus status, String userEmail, boolean isAdmin) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(
                        "Бронирование с id " + bookingId + " не найдено"
                ));

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        "Пользователь с email " + userEmail + " не найден"
                ));

        if (!isAdmin && !booking.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Вы не можете менять статус чужого бронирования");
        }

        booking.setStatus(status);
        bookingRepository.save(booking);

        auditLogService.log(
                AuditActionType.BOOKING_UPDATED,
                currentUser.getId(),
                booking.getId(),
                booking.getResource().getId(),
                "Изменён статус бронирования на: " + status
        );

        NotificationDocument notification = new NotificationDocument();
        notification.setUserId(booking.getUser().getId());
        notification.setType(NotificationType.SYSTEM);
        notification.setMessage("Статус вашего бронирования для ресурса \"" +
                booking.getResource().getName() +
                "\" изменён на: " + status);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationService.sendNotification(notification);
    }
}