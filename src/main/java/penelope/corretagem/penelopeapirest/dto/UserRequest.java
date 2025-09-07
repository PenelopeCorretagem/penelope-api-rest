package penelope.corretagem.penelopeapirest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserRequest {

//    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

//    @NotBlank(message = "O campo CPF é obrigatório")
    @CPF(message = "O CPF informado é inválido")
    @Size(min = 1, max = 100)
    private String cpf;

//    @NotNull(message = "O campo e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    private String email;

//    @NotNull(message = "O campo data de nascimento é obrigatório")
    private Date dtNascimento;

    private Double rendaMensal;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }
}
