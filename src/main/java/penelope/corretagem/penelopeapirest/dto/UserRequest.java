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

@Getter
@Setter
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
}
