package penelope.corretagem.penelopeapirest.data.domain.enums;

public enum Error {
  MESSAGE("message"),
  STATUS("status"),
  TIMESTAMP("timestamp");

  private final String field;

  Error(String field) {
    this.field = field;
  }

  public String getField() {
    return field;
  }
}