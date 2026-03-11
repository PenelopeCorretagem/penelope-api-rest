package penelope.corretagem.penelopeapirest.oldArchiteture.domain.enums;

public enum Status {
  PENDING("agendado"),
  CONCLUDED("concluido"),
  CANCELLED("cancelado");

  private String descricao;

  Status(String status){
    this.descricao = status;
  }

  public String getDescricao() {
    return descricao;
  }
}