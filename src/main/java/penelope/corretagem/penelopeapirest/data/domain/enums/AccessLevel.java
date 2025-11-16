package penelope.corretagem.penelopeapirest.data.domain.enums;

import lombok.Getter;

@Getter
public enum AccessLevel {
  ADMINISTRADOR("Administrador"),
  CLIENTE("Cliente");

  private final String description;

  AccessLevel(String description) {
    this.description = description;
  }
}
