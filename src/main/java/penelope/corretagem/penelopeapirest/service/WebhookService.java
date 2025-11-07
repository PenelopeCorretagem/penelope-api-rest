package penelope.corretagem.penelopeapirest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.CalWebhookRequest;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateAgentEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;
import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.Status;
import penelope.corretagem.penelopeapirest.data.domain.repository.AppointmentRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateAgentRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.EstateRepository;
import penelope.corretagem.penelopeapirest.data.domain.repository.UserRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@Service
public class WebhookService {

  private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
  private final ObjectMapper mapper;
  private final AppointmentRepository appointmentRepository;
  private final UserRepository userRepository;
  private final EstateRepository estateRepository;
  private final EstateAgentRepository agentRepository;

  @Value("${cal.webhook.secret}")
  private String webhookSecret;

  public WebhookService(ObjectMapper mapper, AppointmentRepository agendamentoRepository, UserRepository userRepository, EstateRepository propertyRepository, EstateAgentRepository agentRepository) {
    this.mapper = mapper;
    this.appointmentRepository = agendamentoRepository;
    this.userRepository = userRepository;
    this.estateRepository = propertyRepository;
    this.agentRepository = agentRepository;
    this.mapper.registerModule(new JavaTimeModule());
  }

  public void processAppointment(String rawBody, String signature) {

    if (!verifySignature(rawBody, signature)) {
      logger.warn("Assinatura de Webhook inválida!");

      throw new RuntimeException("Assinatura de Webhook inválida!"); // todo criar exceção customizada
    }
    logger.info("Assinatura de Webhook verificada com sucesso!");

    // 13. Bloco try-catch: Se a conversão do JSON falhar, o app não quebra.
    try {
      // 14. Conversão: Usa o objectMapper para transformar a String (JSON)
      // no nosso record CalWebhook.
      CalWebhookRequest webhook = mapper.readValue(rawBody, CalWebhookRequest.class);

      // 15. Lógica de Negócio: Verifica se o evento é o que nos interessa.
      if ("BOOKING_CREATED".equals(webhook.triggerEvent())) {
        logger.info("Novo agendamento recebido: {}", webhook.payload().title());

        String hostEmail = webhook.payload().organizer().email();

        UserEntity guest = webhook.payload().attendees().stream()
          .filter(attendee -> !attendee.email().equalsIgnoreCase(hostEmail))
          .findFirst()
          .flatMap(attendee -> userRepository.findByEmail(attendee.email()))
          .orElseThrow(() -> new RuntimeException("convidado do agendamento")); // todo criar exceção customizada

        Optional<Map<String, String>> metadata = Optional.ofNullable(webhook.payload().metadata());

        Long imovelId = metadata
          .map(md -> md.get("imovelId"))
          .map(Long::getLong)
          .orElseThrow(() -> new RuntimeException("Imóvel não encontrado nos metadados do agendamento"));

        EstateEntity property = estateRepository.findById(imovelId)
          .orElseThrow(() -> new RuntimeException("Imóvel não encontrado no banco de dados")); // todo criar exceção customizada

        Long agentId = metadata
          .map(md -> md.get("agentId"))
          .map(Long::getLong)
          .orElseThrow(() -> new RuntimeException("Corretor não encontrado nos metadados do agendamento")); // todo criar exceção customizada

        EstateAgentEntity estateAgent = agentRepository.findById(agentId)
          .orElseThrow(() -> new RuntimeException("Corretor não encontrado no banco de dados")); // todo criar exceção customizada

        AppointmentEntity newAppointment = new AppointmentEntity();

        newAppointment.setUser(guest);
        newAppointment.setEstate(property);
        newAppointment.setEstateAgent(estateAgent);
        newAppointment.setStatus(Status.PENDING);
        newAppointment.setStartDateTime(webhook.payload().startTime().toLocalDateTime());
        newAppointment.setEndDateTime(webhook.payload().endTime().toLocalDateTime());

        appointmentRepository.save(newAppointment);
        logger.info("Novo agendamento salvo no banco de dados para: " + guest.getEmail());
      }

    } catch (Exception e) {
      logger.error("Erro ao processar o payload do webhook: {}", e.getMessage());
    }
  }

  private boolean verifySignature(String body, String signature) {
    try {
      // Pega uma instância do algoritmo HmacSHA256.
      Mac mac = Mac.getInstance("HmacSHA256");
      // Cria a chave de criptografia a partir do nosso 'webhookSecret'.
      SecretKeySpec secretKey = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
      // Inicializa o algoritmo com a nossa chave.
      mac.init(secretKey);
      // Calcula o hash (assinatura) do corpo (body) da requisição.
      //O resultado são bytes puros.
      byte[] hashBytes = mac.doFinal(body.getBytes(StandardCharsets.UTF_8));
      // Converte os bytes puros do hash para uma string Base64.
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
