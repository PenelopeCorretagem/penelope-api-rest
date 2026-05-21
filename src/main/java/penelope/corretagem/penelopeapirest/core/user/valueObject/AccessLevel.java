package penelope.corretagem.penelopeapirest.core.user.valueObject;

import lombok.Getter;

@Getter
public enum AccessLevel {
  ADMINISTRADOR(1, "Administrador"),
  CLIENTE(2, "Cliente");

  private final int code;
  private final String description;

  AccessLevel(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public int getCode() {
    return code;
  }

  public String getDisplayName() {
    return description;
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

    boolean isNumeric = trimmed.chars().allMatch(Character::isDigit);
    if (isNumeric) {
      return fromCode(Integer.parseInt(trimmed));
    }

    for (AccessLevel level : values()) {
      if (level.name().equalsIgnoreCase(trimmed)
          || level.getDescription().equalsIgnoreCase(trimmed)
          || level.getDisplayName().equalsIgnoreCase(trimmed)) {
        return level;
      }
    }

    throw new IllegalArgumentException("Nivel de acesso invalido: " + value);
  }
}
