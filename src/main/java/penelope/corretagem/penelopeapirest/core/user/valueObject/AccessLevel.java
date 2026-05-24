package penelope.corretagem.penelopeapirest.core.user.valueObject;

import lombok.Getter;

@Getter
public enum AccessLevel {
  ADMINISTRADOR(1, "Administrador", "administrador"),
  CLIENTE(2, "Cliente", "cliente"),
  CORRETOR(3, "Corretor", "corretor");

  private final int code;
  private final String description;
  private final String externalValue;

  AccessLevel(int code, String description, String externalValue) {
    this.code = code;
    this.description = description;
    this.externalValue = externalValue;
  }

  public int getCode() {
    return code;
  }

  public static AccessLevel fromCode(int code) {
    for (AccessLevel value : values()) {
      if (value.code == code) {
        return value;
      }
    }
    throw new IllegalArgumentException("Codigo de nivel de acesso invalido: " + code);
  }

  public static AccessLevel fromExternalValue(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Nivel de acesso invalido: null");
    }

    String trimmed = value.trim();
    if (trimmed.isEmpty()) {
      throw new IllegalArgumentException("Nivel de acesso invalido: " + value);
    }

    for (AccessLevel level : values()) {
      if (level.externalValue.equals(trimmed)) {
        return level;
      }
    }

    throw new IllegalArgumentException("Nivel de acesso invalido: " + value);
  }

  public String toExternalValue() {
    return externalValue;
  }
}
