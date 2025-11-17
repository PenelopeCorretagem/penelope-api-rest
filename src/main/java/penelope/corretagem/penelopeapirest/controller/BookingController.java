package penelope.corretagem.penelopeapirest.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingListResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingResponse;
import penelope.corretagem.penelopeapirest.service.BookingService;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Lista agendamentos por imóvel
     */
    @GetMapping("/estate/{estateId}")
    public ResponseEntity<BookingListResponse> listBookingsByEstate(
            @PathVariable Long estateId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            BookingListResponse response = bookingService.listBookingsByEstate(estateId, dateFrom, dateTo, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lista agendamentos por usuário
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<BookingListResponse> listBookingsByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            BookingListResponse response = bookingService.listBookingsByUser(userId, dateFrom, dateTo, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lista todos os agendamentos com filtros
     */
    @GetMapping
    public ResponseEntity<BookingListResponse> listAllBookings(
            @RequestParam(required = false) Long eventTypeId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            BookingListResponse response = bookingService.listAllBookings(eventTypeId, userId, dateFrom, dateTo, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Busca um agendamento específico
     */
    @GetMapping("/{bookingUid}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String uid) {
        try {
            BookingResponse response = bookingService.getBooking(uid);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Reagenda um agendamento
     */
    @PatchMapping("/{appointmentId}/reschedule")
    public ResponseEntity<BookingResponse> rescheduleBooking(
            @PathVariable Long appointmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime newStartTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime newEndTime,
            @RequestParam(required = false) String reason) {
        try {
            BookingResponse response = bookingService.rescheduleBooking(appointmentId, newStartTime, newEndTime, reason);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Cancela um agendamento
     */
    @PostMapping("/{appointmentId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long appointmentId,
            @RequestParam(required = false) String reason) {
        try {
            BookingResponse response = bookingService.cancelBooking(appointmentId, reason);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}