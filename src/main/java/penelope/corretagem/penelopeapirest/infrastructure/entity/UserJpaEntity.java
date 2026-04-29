package penelope.corretagem.penelopeapirest.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import penelope.corretagem.penelopeapirest.core.user.valueObject.AccessLevel;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String password;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dateBirth;

    @Column(name = "renda_mensal")
    private BigDecimal monthlyIncome;

    @Column(name = "telefone")
    private String phone;

    @Column(name = "creci")
    private String creci;

    @Column(name = "nivel_acesso")
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    @Column(name = "data_criacao")
    private LocalDate dateCreation;

    @Column(name = "ativo")
    private boolean active = true;

    @Column(name = "token_redefinicao_senha")
    private String passwordResetToken;

    @Column(name = "data_expiracao_token")
    private Date passwordResetTokenExpiry;
}
