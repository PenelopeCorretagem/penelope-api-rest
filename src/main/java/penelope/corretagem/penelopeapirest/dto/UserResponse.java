package penelope.corretagem.penelopeapirest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private String nome;
    private String cpf;
    private String email;
    private Date dtNascimento;
    private Double rendaMensal;
}
