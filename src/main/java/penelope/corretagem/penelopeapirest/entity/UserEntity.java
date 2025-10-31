package penelope.corretagem.penelopeapirest.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do usuário", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    @Schema(description = "E-mail de contato do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "CPF do usuário", example = "123.456.789-00")
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso", nullable = false)
    private NivelAcesso nivelAcesso;

    @Column(nullable = true)
    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01", type = "string", format = "date")
    private Date dtNascimento;

    @Column(nullable = false)
    private LocalDate dtCriacao;

    @Column(nullable = false)
    private boolean ativo;

    @Column
    private String passwordResetToken;

    @Column
    private Date passwordResetTokenExpiry;

    @Schema(description = "Renda mensal do usuário em reais", example = "3500.50")
    private Double rendaMensal;

    public Long getId() {   return id;  }
    public String getNomeCompleto() {   return nomeCompleto;    }
    public String getEmail() {  return email;   }
    public Date getDtNascimento() {  return dtNascimento;    }
    public String getSenha() {  return senha;   }
    public String getPasswordResetToken() {     return passwordResetToken;  }
    public Date getPasswordResetTokenExpiry() {     return passwordResetTokenExpiry;    }
    public NivelAcesso getNivelAcesso() {   return nivelAcesso;     }
    public LocalDate getDtCriacao() {    return dtCriacao;   }
    public boolean isAtivo() {   return ativo;   }
    public String getCpf() {    return cpf;     }
    public Double getRendaMensal() {    return rendaMensal;    }

    public void setId(Long id) {    this.id = id;   }
    public void setNomeCompleto(String nomeCompleto) {  this.nomeCompleto = nomeCompleto;   }
    public void setEmail(String email) {    this.email = email; }
    public void setDtNascimento(Date dtNascimento) {    this.dtNascimento = dtNascimento;   }
    public void setSenha(String senha) {    this.senha = senha; }
    public void setPasswordResetToken(String passwordResetToken) {  this.passwordResetToken = passwordResetToken;   }
    public void setPasswordResetTokenExpiry(Date passwordResetTokenExpiry) {    this.passwordResetTokenExpiry = passwordResetTokenExpiry;   }
    public void setNivelAcesso(NivelAcesso nivelAcesso) {   this.nivelAcesso = nivelAcesso;     }
    public void setDtCriacao(LocalDate dtCriacao) {      this.dtCriacao = dtCriacao;     }
    public void setAtivo(boolean ativo) {   this.ativo = ativo;     }
    public void setCpf(String cpf) {    this.cpf = cpf;    }
    public void setRendaMensal(Double rendaMensal) {    this.rendaMensal = rendaMensal;    }

    public enum NivelAcesso {
        Admin, Corretor, Cliente
    }
}
