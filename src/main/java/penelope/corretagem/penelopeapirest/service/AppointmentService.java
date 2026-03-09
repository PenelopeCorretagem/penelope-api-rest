package penelope.corretagem.penelopeapirest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import penelope.corretagem.penelopeapirest.application.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.*;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;
import penelope.corretagem.penelopeapirest.infrastructure.specification.AppointmentSpecification;
import org.springframework.data.jpa.domain.Specification;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;
import penelope.corretagem.penelopeapirest.data.domain.repository.AppointmentRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final EstateRepository estateRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            EstateRepository estateRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.estateRepository = estateRepository;
    }

    /**
     * Busca agendamento por ID
     */
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointment(Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado: " + id));
        
        return mapToResponse(appointment);
    }

    /**
     * Busca agendamentos com filtros usando Specification
     */
    @Transactional(readOnly = true)
    public Page<AppointmentResponse> searchAppointments(
            Long clientId,
            Long estateAgentId,
            Long estateId,
            Status status,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String clientEmail,
            String agentEmail,
            String estateTitle,
            Boolean hasCalBooking,
            Boolean onlyActive,
            Pageable pageable) {
        
        Specification<AppointmentEntity> spec = AppointmentSpecification.withFilters(
            clientId, estateAgentId, estateId, status, startDate, endDate, 
            clientEmail, agentEmail, estateTitle
        );

        if (hasCalBooking != null) {
            if (hasCalBooking) {
                spec = spec.and(AppointmentSpecification.hasCalBookingId());
            } else {
                spec = spec.and(AppointmentSpecification.hasNoCalBookingId());
            }
        }

        if (onlyActive != null && onlyActive) {
            spec = spec.and(AppointmentSpecification.isActive());
        }

        Page<AppointmentEntity> appointments = appointmentRepository.findAll(spec, pageable);
        
        return appointments.map(this::mapToResponse);
    }

    /**
     * Mapeia entidade para DTO de resposta
     */
    private AppointmentResponse mapToResponse(AppointmentEntity appointment) {
        UserResponse client = mapUserToResponse(appointment.getClient());
        UserResponse estateAgent = mapUserToResponse(appointment.getEstateAgent());
        EstateResponse estate = mapEstateToResponse(appointment.getEstate());

        return new AppointmentResponse(
                appointment.getId(),
                client,
                estateAgent,
                estate,
                appointment.getDurationMinutes(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus(),
                appointment.getCalBookingId(),
                appointment.getDateAppointment() != null ? appointment.getDateAppointment() : appointment.getStartDateTime(),
                LocalDateTime.now()
        );
    }

    /**
     * Mapeia User para UserResponse (simplificado)
     */
    private UserResponse mapUserToResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                null,
                user.getCpf(),
                user.getDateBirth(),
                user.getMonthlyIncome(),
                user.getPhone(),
                user.getCreci(),
                user.getAccessLevel(),
                user.getDateCreation(),
                user.isActive(),
                null,
                null
        );
    }

    /**
     * Mapeia Estate para EstateResponse (simplificado)
     */
    private EstateResponse mapEstateToResponse(EstateEntity estate) {
        return new EstateResponse(
                estate.getId(),
                estate.getTitle(),
                estate.getDescription(),
                estate.getArea(),
                estate.getNumberOfRooms(),
                estate.getType().name(),
                null,
                null,
                null,
                null
        );
    }
}
