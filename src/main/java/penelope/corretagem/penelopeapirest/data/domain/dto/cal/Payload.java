package penelope.corretagem.penelopeapirest.data.domain.dto.cal;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record Payload(
  String title,
  String status,
  OffsetDateTime startTime,
  OffsetDateTime endTime,
  Long eventTypeId,
  List<Attendee> attendees,
  Organizer organizer,
  Map<String, String> metadata
) {}