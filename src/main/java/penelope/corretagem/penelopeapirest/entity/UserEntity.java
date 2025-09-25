package penelope.corretagem.penelopeapirest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = true)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private Date dtNascimento;

    @Column(nullable = true)
    private Double rendaMensal;

    @Column(nullable = false)
    private String senha;

    @Column
    private String passwordResetToken;

    @Column
    private Date passwordResetTokenExpiry;

    public Long getId() {   return id;  }
    public String getNomeCompleto() {   return nomeCompleto;    }
    public String getCpf() {    return cpf;    }
    public String getEmail() {  return email;   }
    public Date getDtNascimento() {  return dtNascimento;    }
    public Double getRendaMensal() {    return rendaMensal; }
    public String getSenha() {  return senha;   }
    public String getPasswordResetToken() {     return passwordResetToken;  }
    public Date getPasswordResetTokenExpiry() {     return passwordResetTokenExpiry;    }

    public void setCpf(String cpf) {    this.cpf = cpf;    }
    public void setId(Long id) {    this.id = id;   }
    public void setNomeCompleto(String nomeCompleto) {  this.nomeCompleto = nomeCompleto;   }
    public void setEmail(String email) {    this.email = email; }
    public void setDtNascimento(Date dtNascimento) {    this.dtNascimento = dtNascimento;   }
    public void setRendaMensal(Double rendaMensal) {    this.rendaMensal = rendaMensal; }
    public void setSenha(String senha) {    this.senha = senha; }
    public void setPasswordResetToken(String passwordResetToken) {  this.passwordResetToken = passwordResetToken;   }
    public void setPasswordResetTokenExpiry(Date passwordResetTokenExpiry) {    this.passwordResetTokenExpiry = passwordResetTokenExpiry;   }
}
