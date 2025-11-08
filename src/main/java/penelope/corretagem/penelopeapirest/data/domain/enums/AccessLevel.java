package penelope.corretagem.penelopeapirest.data.domain.enums;

import lombok.Getter;

@Getter
public enum AccessLevel {
  ADMIN("admin"),
  CLIENT("cliente");

  private final String description;

  AccessLevel(String description) {
    this.description = description;
  }
}
