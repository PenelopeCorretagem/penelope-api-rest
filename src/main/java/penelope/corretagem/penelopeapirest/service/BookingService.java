package penelope.corretagem.penelopeapirest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.clients.CalClient;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.booking.*;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EventTypeEntity;
import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final CalClient calClient;
    private final AppointmentRepository appointmentRepository;
    private final AdvertisementRepository advertisementRepository;

    public BookingService(CalClient calClient, 
                         AppointmentRepository appointmentRepository,
                          AdvertisementRepository advertisementRepository) {
        this.calClient = calClient;
        this.appointmentRepository = appointmentRepository;
        this.advertisementRepository = advertisementRepository;
    }

    /**
     * Lista bookings por Event Type (imóvel)
     */
    public BookingListResponse listBookingsByEstate(Long estateId, LocalDate dateFrom, 
                                                   LocalDate dateTo, Integer page, Integer size) {
        logger.info("Listando agendamentos para o imóvel ID: {}", estateId);

        AdvertisementEntity advertisement = advertisementRepository.findByEstateId(estateId);
        EventTypeEntity eventType = advertisement.getEventType();

        if (eventType == null) {
            throw new RuntimeException("Anúncio não possui Event Type associado");
        }

        try {
            return calClient.listBookings(
                    new BookingFilterRequest(
                            eventType.getId(),
                            null, // userId
                            dateFrom,
                            dateTo,
                            page,
                            size
                    )
            );
        } catch (Exception e) {
            logger.error("Erro ao listar agendamentos do imóvel {}: {}", estateId, e.getMessage(), e);
            throw new RuntimeException("Falha ao listar agendamentos: " + e.getMessage(), e);
        }
    }

    /**
     * Lista bookings por usuário
     */
    public BookingListResponse listBookingsByUser(Long userId, LocalDate dateFrom, 
                                                 LocalDate dateTo, Integer page, Integer size) {
        logger.info("Listando agendamentos para o usuário ID: {}", userId);
        
        try {
            return calClient.listBookings(
                    new BookingFilterRequest(
                            null, // eventTypeId
                            userId,
                            dateFrom,
                            dateTo,
                            page,
                            size
                    )
            );
        } catch (Exception e) {
            logger.error("Erro ao listar agendamentos do usuário {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Falha ao listar agendamentos: " + e.getMessage(), e);
        }
    }

    /**
     * Lista todos os bookings com filtros opcionais
     */
    public BookingListResponse listAllBookings(Long eventTypeId, Long userId, 
                                              LocalDate dateFrom, LocalDate dateTo, 
                                              Integer page, Integer size) {
        logger.info("Listando todos os agendamentos com filtros");
        
        try {
            return calClient.listBookings(new BookingFilterRequest(eventTypeId, userId, dateFrom, dateTo, page, size));
        } catch (Exception e) {
            logger.error("Erro ao listar agendamentos: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao listar agendamentos: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um booking específico
     */
    public BookingResponse getBooking(String uid) {
        logger.info("Buscando agendamento ID: {}", uid);
        
        try {
            return calClient.getBooking(uid);
        } catch (Exception e) {
            logger.error("Erro ao buscar agendamento {}: {}", uid, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar agendamento: " + e.getMessage(), e);
        }
    }

    /**
     * Reagenda um booking existente
     */
    public BookingResponse rescheduleBooking(Long appointmentId, OffsetDateTime newStartTime, 
                                           OffsetDateTime newEndTime, String reason) {
        logger.info("Reagendando appointment ID: {}", appointmentId);
        
        // Buscar o agendamento local para obter o booking ID do Cal.com
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado: " + appointmentId));

        if (appointment.getId() == null) {
            throw new RuntimeException("Agendamento não possui ID do Cal.com associado");
        }

        BookingUpdateRequest request = new BookingUpdateRequest(newStartTime, newEndTime, reason);

        try {
            BookingResponse response = calClient.updateBooking(appointment.getId(), request);
            
            if (response != null) {
                logger.info("Agendamento {} reagendado com sucesso no Cal.com", appointment.getId());
            }
            
            return response;
        } catch (Exception e) {
            logger.error("Erro ao reagendar booking {} do appointment {}: {}", 
                    appointment.getId(), appointmentId, e.getMessage(), e);
            throw new RuntimeException("Falha ao reagendar agendamento: " + e.getMessage(), e);
        }
    }

    /**
     * Cancela um booking
     */
    public BookingResponse cancelBooking(Long appointmentId, String reason) {
        logger.info("Cancelando appointment ID: {}", appointmentId);
        
        // Buscar o agendamento local
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado: " + appointmentId));

        if (appointment.getId() == null) {
            throw new RuntimeException("Agendamento não possui ID do Cal.com associado");
        }

        BookingCancelRequest request = new BookingCancelRequest(reason, false);

        try {
            BookingResponse response = calClient.cancelBooking(appointment.getId(), request);
            
            if (response != null) {
                logger.info("Agendamento {} cancelado com sucesso no Cal.com", appointment.getId());
                // O webhook BOOKING_CANCELLED irá atualizar o banco local
            }
            
            return response;
        } catch (Exception e) {
            logger.error("Erro ao cancelar booking {} do appointment {}: {}", 
                    appointment.getId(), appointmentId, e.getMessage(), e);
            throw new RuntimeException("Falha ao cancelar agendamento: " + e.getMessage(), e);
        }
    }

    /**
     * Método utilitário para converter LocalDateTime para OffsetDateTime
     */
    private OffsetDateTime toOffsetDateTime(java.time.LocalDateTime localDateTime) {
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}