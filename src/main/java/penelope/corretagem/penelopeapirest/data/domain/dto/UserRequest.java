package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;
import java.time.LocalDate;

public record UserRequest(
  String username,
  String email,
  String password,
  AccessLevel accessLevel,
  LocalDate dateCreation,
  boolean active
) {}