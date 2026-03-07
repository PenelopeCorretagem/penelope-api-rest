package penelope.corretagem.penelopeapirest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.CalWebhookRequest;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;
import penelope.corretagem.penelopeapirest.data.domain.repository.AdvertisementRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.AppointmentRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.UserRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Service
public class WebhookService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);

    private final ObjectMapper mapper;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final EstateRepository estateRepository;
    private final AdvertisementRepository advertisementRepository;

    @Value("${calcom.webhook.secret}")
    private String webhookSecret;

    public WebhookService(
            ObjectMapper mapper,
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            EstateRepository estateRepository,
            AdvertisementRepository advertisementRepository) {
        this.mapper = mapper;
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.estateRepository = estateRepository;
        this.mapper.registerModule(new JavaTimeModule());
        this.advertisementRepository = advertisementRepository;
    }

    public void processAppointment(String rawBody, String signature) {

        if (!verifySignature(rawBody, signature)) {
            logger.warn("Assinatura de Webhook inválida!");
            throw new RuntimeException("Assinatura de Webhook inválida!");
        }

        logger.info("Assinatura de Webhook verificada com sucesso!");

        try {
            CalWebhookRequest webhook = mapper.readValue(rawBody, CalWebhookRequest.class);

            switch (webhook.triggerEvent()) {
                case "BOOKING_CREATED" -> handleBookingCreated(webhook);
                case "BOOKING_RESCHEDULED" -> handleBookingRescheduled(webhook);
                case "BOOKING_CANCELLED" -> handleBookingCancelled(webhook);
                default -> logger.warn("Tipo de evento não reconhecido: {}", webhook.triggerEvent());
            }

        } catch (Exception e) {
            logger.error("Erro ao processar o payload do webhook: {}", e.getMessage(), e);
        }
    }

    private void handleBookingCreated(CalWebhookRequest webhook) {
        logger.info("Novo agendamento recebido: {}", webhook.payload().title());

        String hostEmail = webhook.payload().organizer().email();

        User guest = webhook.payload().attendees().stream()
                .filter(attendee -> !attendee.email().equalsIgnoreCase(hostEmail))
                .findFirst()
                .flatMap(attendee -> userRepository.findByEmail(attendee.email()))
                .orElseThrow(() -> new RuntimeException("Convidado do agendamento não encontrado"));

        Advertisement advertisement = advertisementRepository.findByEventTypeId(webhook.payload().eventTypeId());

        Estate property = estateRepository.findById(advertisement.getEstate().getId())
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado no banco de dados"));

        User agent = userRepository.findById(advertisement.getResponsible().getId())
                .orElseThrow(() -> new RuntimeException("Corretor não encontrado no banco de dados"));

        AppointmentEntity newAppointment = new AppointmentEntity();
        newAppointment.setClient(guest);
        newAppointment.setEstateAgent(agent);
        newAppointment.setEstate(property);
        newAppointment.setStatus(Status.PENDING);
        newAppointment.setStartDateTime(webhook.payload().startTime().toLocalDateTime());
        newAppointment.setEndDateTime(webhook.payload().endTime().toLocalDateTime());
        newAppointment.setDateAppointment(webhook.payload().startTime().toLocalDateTime());
        newAppointment.setCalBookingId(webhook.payload().bookingId());

        int duration = (int) Duration.between(
                webhook.payload().startTime().toLocalDateTime(),
                webhook.payload().endTime().toLocalDateTime()
        ).toMinutes();

        newAppointment.setDurationMinutes(duration);

        appointmentRepository.save(newAppointment);
        logger.info("Novo agendamento salvo no banco de dados para o cliente: {}", guest.getEmail());
    }

    private void handleBookingRescheduled(CalWebhookRequest webhook) {
        logger.info("Agendamento reagendado recebido: {}", webhook.payload().title());

        Optional<Map<String, String>> metadata = Optional.ofNullable(webhook.payload().metadata());
        Long calBookingId = metadata
                .map(md -> md.get("bookingId"))
                .map(Long::parseLong)
                .orElse(null);

        AppointmentEntity appointment = null;

        if (calBookingId != null) {
            appointment = appointmentRepository.findByCalBookingId(calBookingId)
                    .orElse(null);
        }

        if (appointment == null) {
            String clientEmail = webhook.payload().attendees().stream()
                    .filter(attendee -> !attendee.email().equalsIgnoreCase(webhook.payload().organizer().email()))
                    .findFirst()
                    .map(attendee -> attendee.email())
                    .orElse(null);

            if (clientEmail != null) {
                User client = userRepository.findByEmail(clientEmail).orElse(null);
                if (client != null) {
                    appointment = appointmentRepository.findByClientAndStatus(client, Status.PENDING)
                            .stream()
                            .findFirst()
                            .orElse(null);
                }
            }
        }

        if (appointment != null) {
            appointment.setStartDateTime(webhook.payload().startTime().toLocalDateTime());
            appointment.setEndDateTime(webhook.payload().endTime().toLocalDateTime());
            appointment.setDateAppointment(webhook.payload().startTime().toLocalDateTime());

            int duration = (int) Duration.between(
                    webhook.payload().startTime().toLocalDateTime(),
                    webhook.payload().endTime().toLocalDateTime()
            ).toMinutes();
            appointment.setDurationMinutes(duration);

            appointmentRepository.save(appointment);
            logger.info("Agendamento {} reagendado com sucesso", appointment.getId());
        } else {
            logger.warn("Agendamento não encontrado para reagendamento: {}", webhook.payload().title());
        }
    }

    private void handleBookingCancelled(CalWebhookRequest webhook) {
        logger.info("Agendamento cancelado recebido: {}", webhook.payload().title());

        Optional<Map<String, String>> metadata = Optional.ofNullable(webhook.payload().metadata());
        Long calBookingId = metadata
                .map(md -> md.get("bookingId"))
                .map(Long::parseLong)
                .orElse(null);

        AppointmentEntity appointment = null;

        if (calBookingId != null) {
            appointment = appointmentRepository.findByCalBookingId(calBookingId)
                    .orElse(null);
        }

        if (appointment == null) {
            String clientEmail = webhook.payload().attendees().stream()
                    .filter(attendee -> !attendee.email().equalsIgnoreCase(webhook.payload().organizer().email()))
                    .findFirst()
                    .map(attendee -> attendee.email())
                    .orElse(null);

            if (clientEmail != null) {
                User client = userRepository.findByEmail(clientEmail).orElse(null);
                if (client != null) {
                    appointment = appointmentRepository.findByClientAndStartDateTime(
                            client, 
                            webhook.payload().startTime().toLocalDateTime()
                    ).orElse(null);
                }
            }
        }

        if (appointment != null) {
            appointment.setStatus(Status.CANCELLED);
            appointmentRepository.save(appointment);
            logger.info("Agendamento {} cancelado com sucesso", appointment.getId());
        } else {
            logger.warn("Agendamento não encontrado para cancelamento: {}", webhook.payload().title());
        }
    }

    private boolean verifySignature(String body, String signature) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);

            byte[] hashBytes = mac.doFinal(body.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return MessageDigest.isEqual(
                    signature.getBytes(StandardCharsets.UTF_8),
                    hexString.toString().getBytes(StandardCharsets.UTF_8)
            );

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("Erro ao verificar assinatura do webhook", e);
            return false;
        }
    }
}
