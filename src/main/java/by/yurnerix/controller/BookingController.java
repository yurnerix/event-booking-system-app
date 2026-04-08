package by.yurnerix.controller;

import by.yurnerix.dto.BookingResponse;
import by.yurnerix.dto.CreateBookingRequest;
import by.yurnerix.dto.ResourceResponse;
import by.yurnerix.enums.BookingStatus;
import by.yurnerix.service.BookingService;
import by.yurnerix.service.ResourceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final ResourceService resourceService;

    public BookingController(BookingService bookingService, ResourceService resourceService) {
        this.bookingService = bookingService;
        this.resourceService = resourceService;
    }

    @GetMapping
    public String getBookings(Model model, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<BookingResponse> bookings = isAdmin
                ? bookingService.getAllBookings()
                : bookingService.getBookingsForUser(authentication.getName());

        model.addAttribute("bookings", bookings);
        model.addAttribute("statuses", BookingStatus.values());
        model.addAttribute("isAdmin", isAdmin);

        return "bookings";
    }

    @GetMapping("/create")
    public String showCreateBookingPage(Model model) {
        List<ResourceResponse> resources = resourceService.getAllResources();
        model.addAttribute("createBookingRequest", new CreateBookingRequest());
        model.addAttribute("resources", resources);
        return "create-booking";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute CreateBookingRequest createBookingRequest,
                                Authentication authentication,
                                Model model) {
        try {
            bookingService.createBooking(createBookingRequest, authentication.getName());
            return "redirect:/bookings";
        } catch (RuntimeException e) {
            List<ResourceResponse> resources = resourceService.getAllResources();
            model.addAttribute("resources", resources);
            model.addAttribute("createBookingRequest", createBookingRequest);
            model.addAttribute("error", e.getMessage());
            return "create-booking";
        }
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        bookingService.cancelBooking(id, authentication.getName(), isAdmin);
        return "redirect:/bookings";
    }

    @PostMapping("/status/{id}")
    public String updateBookingStatus(@PathVariable Long id,
                                      @RequestParam BookingStatus status,
                                      Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        bookingService.updateBookingStatus(id, status, authentication.getName(), isAdmin);
        return "redirect:/bookings";
    }
}