package penelope.corretagem.penelopeapirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingListResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingResponse;
import penelope.corretagem.penelopeapirest.service.BookingService;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/cal/bookings")
@CrossOrigin(origins = "*")
@Tag(name = "Cal Agendamentos", description = "Gerencia agendamentos")
public class BookingController {

  private final BookingService bookingService;

  public BookingController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  /**
   * Lista agendamentos por imóvel
   */
  @GetMapping("/estate/{estateId}")
  @Operation(
    summary = "Lista agendamentos por imóvel",
    description = "Retorna uma lista paginada de agendamentos associados a um imóvel específico, com filtros opcionais de data.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
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
  @Operation(
    summary = "Lista agendamentos por usuário",
    description = "Retorna uma lista paginada de agendamentos associados a um usuário específico, com filtros opcionais de data.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
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
  @Operation(
    summary = "Lista todos os agendamentos com filtros",
    description = "Retorna uma lista paginada de todos os agendamentos, com filtros opcionais por tipo de evento, usuário e intervalo de datas.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
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
  @Operation(
    summary = "Busca um agendamento específico",
    description = "Retorna os detalhes de um agendamento específico com base no UID fornecido.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Agendamento retornado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
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
  @Operation(
    summary = "Reagenda um agendamento",
    description = "Permite reprogramar um agendamento existente para um novo horário.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Agendamento reprogramado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
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
  @Operation(
    summary = "Cancela um agendamento",
    description = "Permite cancelar um agendamento existente, opcionalmente fornecendo um motivo para o cancelamento.")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
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