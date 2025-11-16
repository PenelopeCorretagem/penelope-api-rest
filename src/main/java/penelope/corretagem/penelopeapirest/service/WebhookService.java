//package penelope.corretagem.penelopeapirest.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import penelope.corretagem.penelopeapirest.data.domain.dto.cal.CalWebhookRequest;
//import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
//import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
//import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;
//import penelope.corretagem.penelopeapirest.data.domain.enums.Status;
//import penelope.corretagem.penelopeapirest.data.domain.repository.AppointmentRepository;
//import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;
//import penelope.corretagem.penelopeapirest.data.domain.repository.UserRepository;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.time.Duration;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class WebhookService {
//
//    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
//
//    private final ObjectMapper mapper;
//    private final AppointmentRepository appointmentRepository;
//    private final UserRepository userRepository;
//    private final EstateRepository estateRepository;
//
//    @Value("${cal.webhook.secret}")
//    private String webhookSecret;
//
//    public WebhookService(
//            ObjectMapper mapper,
//            AppointmentRepository appointmentRepository,
//            UserRepository userRepository,
//            EstateRepository estateRepository
//    ) {
//        this.mapper = mapper;
//        this.appointmentRepository = appointmentRepository;
//        this.userRepository = userRepository;
//        this.estateRepository = estateRepository;
//        this.mapper.registerModule(new JavaTimeModule());
//    }
//
//    public void processAppointment(String rawBody, String signature) {
//
//        if (!verifySignature(rawBody, signature)) {
//            logger.warn("Assinatura de Webhook inválida!");
//            throw new RuntimeException("Assinatura de Webhook inválida!"); // TODO: criar exceção customizada
//        }
//
//        logger.info("Assinatura de Webhook verificada com sucesso!");
//
//        try {
//            // Converte o JSON do webhook para o DTO
//            CalWebhookRequest webhook = mapper.readValue(rawBody, CalWebhookRequest.class);
//
//            if ("BOOKING_CREATED".equals(webhook.triggerEvent())) {
//                logger.info("Novo agendamento recebido: {}", webhook.payload().title());
//
//                // E-mail do organizador (host)
//                String hostEmail = webhook.payload().organizer().email();
//
//                // Busca o convidado (cliente) no sistema
//                UserEntity guest = webhook.payload().attendees().stream()
//                        .filter(attendee -> !attendee.email().equalsIgnoreCase(hostEmail))
//                        .findFirst()
//                        .flatMap(attendee -> userRepository.findByEmail(attendee.email()))
//                        .orElseThrow(() -> new RuntimeException("Convidado do agendamento não encontrado")); // TODO: exceção customizada
//
//                Optional<Map<String, String>> metadata = Optional.ofNullable(webhook.payload().metadata());
//
//                // Obtém IDs dos metadados
//                Long estateId = metadata
//                        .map(md -> md.get("imovelId"))
//                        .map(Long::parseLong)
//                        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado nos metadados"));
//
//                Long agentId = metadata
//                        .map(md -> md.get("agentId"))
//                        .map(Long::parseLong)
//                        .orElseThrow(() -> new RuntimeException("Corretor não encontrado nos metadados"));
//
//                // Busca o imóvel e o corretor no banco
//                EstateEntity property = estateRepository.findById(estateId)
//                        .orElseThrow(() -> new RuntimeException("Imóvel não encontrado no banco de dados"));
//
//                UserEntity agent = userRepository.findById(agentId)
//                        .orElseThrow(() -> new RuntimeException("Corretor não encontrado no banco de dados"));
//
//                // Cria novo agendamento
//                AppointmentEntity newAppointment = new AppointmentEntity();
//                newAppointment.setClient(guest);
//                newAppointment.setEstateAgent(agent);
//                newAppointment.setEstate(property);
//                newAppointment.setStatus(Status.PENDING);
//                newAppointment.setStartDateTime(webhook.payload().startTime().toLocalDateTime());
//                newAppointment.setEndDateTime(webhook.payload().endTime().toLocalDateTime());
//                newAppointment.setDateAppointment(webhook.payload().startTime().toLocalDateTime());
//
//                // Calcula a duração em minutos
//                int duration = (int) Duration.between(
//                        webhook.payload().startTime().toLocalDateTime(),
//                        webhook.payload().endTime().toLocalDateTime()
//                ).toMinutes();
//
//                newAppointment.setDurationMinutes(duration);
//
//                appointmentRepository.save(newAppointment);
//                logger.info("Novo agendamento salvo no banco de dados para o cliente: {}", guest.getEmail());
//            }
//
//        } catch (Exception e) {
//            logger.error("Erro ao processar o payload do webhook: {}", e.getMessage(), e);
//        }
//    }
//
//    private boolean verifySignature(String body, String signature) {
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//            mac.init(secretKey);
//
//            byte[] hashBytes = mac.doFinal(body.getBytes(StandardCharsets.UTF_8));
//
//            // Converte hash para hexadecimal
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            // Compara de forma segura
//            return MessageDigest.isEqual(
//                    signature.getBytes(StandardCharsets.UTF_8),
//                    hexString.toString().getBytes(StandardCharsets.UTF_8)
//            );
//
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            logger.error("Erro ao verificar assinatura do webhook", e);
//            return false;
//        }
//    }
//}
