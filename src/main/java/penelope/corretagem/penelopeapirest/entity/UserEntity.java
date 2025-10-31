package penelope.corretagem.penelopeapirest.entity;

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
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso", nullable = false)
    private NivelAcesso nivelAcesso;

    @Column(nullable = true)
    private Date dtNascimento;

    @Column(nullable = false)
    private LocalDate dtCriacao;

    @Column(nullable = false)
    private boolean ativo;

    @Column
    private String passwordResetToken;

    @Column
    private Date passwordResetTokenExpiry;

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

    public enum NivelAcesso {
        Admin, Corretor, Cliente
    }
}
