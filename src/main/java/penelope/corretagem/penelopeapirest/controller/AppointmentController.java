package penelope.corretagem.penelopeapirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.AppointmentCreateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AppointmentResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AppointmentUpdateRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingListResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.BookingResponse;
import penelope.corretagem.penelopeapirest.service.AppointmentService;
import penelope.corretagem.penelopeapirest.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;

@Tag(name = "Agendamentos", description = "Gerenciamento de agendamentos integrado com Cal.com")
@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final BookingService bookingService;

    public AppointmentController(AppointmentService appointmentService, BookingService bookingService) {
        this.appointmentService = appointmentService;
        this.bookingService = bookingService;
    }

    @Operation(summary = "AVISO: Use o componente Cal.com no frontend", 
               description = "A criação de agendamentos deve ser feita via componente Cal.com no frontend. Este endpoint é apenas para casos especiais.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Use o componente Cal.com no frontend"),
            @ApiResponse(responseCode = "201", description = "Agendamento criado (apenas para casos especiais)")
    })
    @PostMapping
    public ResponseEntity<?> createAppointment(
            @Valid @RequestBody AppointmentCreateRequest request) {
        // Retornar aviso para usar o componente Cal.com
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Use o componente de agendamento do Cal.com no frontend. Os agendamentos serão criados automaticamente via webhook.");
    }

    @Operation(summary = "Buscar agendamento por ID", description = "Retorna os detalhes de um agendamento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointment(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        try {
            AppointmentResponse response = appointmentService.getAppointment(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @Operation(summary = "Listar agendamentos com filtros", 
               description = "Lista agendamentos com filtros opcionais e paginação")
    @GetMapping
    public ResponseEntity<Page<AppointmentResponse>> searchAppointments(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long estateAgentId,
            @RequestParam(required = false) Long estateId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) String clientEmail,
            @RequestParam(required = false) String agentEmail,
            @RequestParam(required = false) String estateTitle,
            @RequestParam(required = false) Boolean hasCalBooking,
            @RequestParam(required = false, defaultValue = "true") Boolean onlyActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startDateTime") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            
            Page<AppointmentResponse> responses = appointmentService.searchAppointments(
                clientId, estateAgentId, estateId, status, startDate, endDate,
                clientEmail, agentEmail, estateTitle, hasCalBooking, onlyActive, pageable
            );
            
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



    @Operation(summary = "Reagendar agendamento via Cal.com", 
               description = "Reagenda um agendamento no Cal.com. A atualização será refletida via webhook.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação de reagendamento enviada"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Agendamento não possui booking Cal.com")
    })
    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<?> rescheduleAppointment(
            @Parameter(description = "ID do agendamento") @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequest request) {
        try {
            // Buscar agendamento para validar e obter calBookingId
            AppointmentResponse appointment = appointmentService.getAppointment(id);
            
            if (appointment.calBookingId() == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Agendamento não possui integração Cal.com"));
            }

            // Converter para OffsetDateTime
            java.time.OffsetDateTime newStartTime = request.startDateTime().atOffset(java.time.OffsetDateTime.now().getOffset());
            java.time.OffsetDateTime newEndTime = (request.endDateTime() != null) 
                ? request.endDateTime().atOffset(java.time.OffsetDateTime.now().getOffset())
                : newStartTime.plusMinutes(appointment.durationMinutes());

            // Reagendar via BookingService - webhook atualizará o banco
            BookingResponse bookingResponse = bookingService.rescheduleBooking(id, newStartTime, newEndTime, "Reagendamento via API");
            
            return ResponseEntity.ok(Map.of(
                "message", "Reagendamento enviado para Cal.com. Aguarde atualização via webhook.",
                "appointmentId", id,
                "calBookingId", appointment.calBookingId(),
                "newStartTime", newStartTime,
                "newEndTime", newEndTime
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Cancelar agendamento via Cal.com", 
               description = "Cancela um agendamento no Cal.com. O status será atualizado via webhook.")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(
            @Parameter(description = "ID do agendamento") @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        try {
            // Buscar agendamento para validar e obter calBookingId
            AppointmentResponse appointment = appointmentService.getAppointment(id);
            
            if (appointment.calBookingId() == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Agendamento não possui integração Cal.com"));
            }

            // Cancelar via BookingService - webhook atualizará o status
            BookingResponse bookingResponse = bookingService.cancelBooking(id, reason);
            
            return ResponseEntity.ok(Map.of(
                "message", "Cancelamento enviado para Cal.com. Aguarde atualização via webhook.",
                "appointmentId", id,
                "calBookingId", appointment.calBookingId(),
                "reason", reason != null ? reason : "Sem motivo especificado"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "AVISO: Confirmação via Cal.com", 
               description = "Confirmação deve ser feita via Cal.com. Status será atualizado por webhook.")
    @PostMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of(
                    "error", "Confirmação deve ser feita via Cal.com",
                    "message", "Use a interface do Cal.com para confirmar agendamentos. O status será sincronizado via webhook."
                ));
    }

    @Operation(summary = "AVISO: Finalização via Cal.com", 
               description = "Finalização deve ser processada via Cal.com. Status será atualizado por webhook.")
    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeAppointment(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of(
                    "error", "Finalização deve ser processada via Cal.com",
                    "message", "Use a interface do Cal.com para finalizar agendamentos. O status será sincronizado via webhook."
                ));
    }


}
