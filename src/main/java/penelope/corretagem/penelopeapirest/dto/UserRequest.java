package penelope.corretagem.penelopeapirest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Objeto de requisição para criação ou atualização de usuário")
public class UserRequest {

    @Schema(description = "Nome completo do usuário", example = "João da Silva", required = true)
    @NotBlank(message = "O campo nome é obrigatório")
    private String nomeCompleto;

    @Schema(description = "CPF do usuário", example = "123.456.789-00", required = true)
    @CPF(message = "O CPF informado é inválido")
    @Size(min = 1, max = 100)
    private String cpf;

    @Schema(description = "E-mail de contato do usuário", example = "joao@email.com", required = true)
    @NotNull(message = "O campo e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    private String email;

    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01", required = true, type = "string", format = "date")
    private Date dtNascimento;

    @Schema(description = "Renda mensal do usuário em reais", example = "3500.50")
    private Double rendaMensal;

    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;

    // Getters e Setters
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }

    public Double getRendaMensal() { return rendaMensal; }
    public void setRendaMensal(Double rendaMensal) { this.rendaMensal = rendaMensal; }

    public String getSenha() { return senha;    }
    public void setSenha(String senha) {    this.senha = senha;    }
}
