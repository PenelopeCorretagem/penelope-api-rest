package penelope.corretagem.penelopeapirest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidade do usuário armazenada no banco de dados")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Identificador único do usuário", example = "1")
    private Long id;

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String nome;

    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "E-mail de contato do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01", type = "string", format = "date")
    private Date dtNascimento;

    @Schema(description = "Renda mensal do usuário em reais", example = "3500.50")
    private Double rendaMensal;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }

    public Double getRendaMensal() { return rendaMensal; }
    public void setRendaMensal(Double rendaMensal) { this.rendaMensal = rendaMensal; }
}
