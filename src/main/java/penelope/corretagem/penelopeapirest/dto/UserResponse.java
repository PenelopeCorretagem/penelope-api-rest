package penelope.corretagem.penelopeapirest.dto;

import java.util.Date;

public class UserResponse {
    private String nome;
    private String cpf;
    private String email;
    private Date dtNascimento;
    private Double rendaMensal;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public Double getRendaMensal() {
        return rendaMensal;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public void setRendaMensal(Double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }
}
