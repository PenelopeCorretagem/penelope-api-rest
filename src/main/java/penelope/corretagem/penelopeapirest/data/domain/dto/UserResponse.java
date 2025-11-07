package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;
import java.time.LocalDate;

public record UserResponse(
  long id,
  String username,
  String email,
  AccessLevel accessLevel,
  LocalDate dateCreation,
  boolean active
) {}