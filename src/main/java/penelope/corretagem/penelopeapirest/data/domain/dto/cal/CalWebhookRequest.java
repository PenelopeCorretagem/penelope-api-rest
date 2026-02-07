package penelope.corretagem.penelopeapirest.data.domain.dto.cal;

import java.time.OffsetDateTime;

public record CalWebhookRequest(
  String triggerEvent, // ex: "BOOKING_CREATED"
  OffsetDateTime createdAt,
  Payload payload
) {}
