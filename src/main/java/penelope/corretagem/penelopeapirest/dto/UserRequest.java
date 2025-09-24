package penelope.corretagem.penelopeapirest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

public class UserRequest {

    @NotBlank(message = "O campo nome completo é obrigatório")
    private String nomeCompleto;
    @CPF(message = "O CPF informado é inválido")
    private String cpf;
    @NotBlank(message = "O campo e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    private String email;
    private Date dtNascimento;
    private Double rendaMensal;
    @NotBlank(message = "O campo senha é obrigatório")
    private String senha;

    public String getNomeCompleto() { return this.nomeCompleto;      }
    public String getCpf() {    return cpf;     }
    public String getEmail() {      return email;         }
    public Date getDtNascimento() {     return dtNascimento;       }
    public Double getRendaMensal() {    return rendaMensal;       }
    public String getSenha() {      return senha;        }

    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public void setCpf(String cpf) {    this.cpf = cpf;     }
    public void setEmail(String email) {    this.email = email;    }
    public void setDtNascimento(Date dtNascimento) {    this.dtNascimento = dtNascimento;    }
    public void setRendaMensal(Double rendaMensal) {    this.rendaMensal = rendaMensal;    }
    public void setSenha(String senha) {    this.senha = senha;    }
}
