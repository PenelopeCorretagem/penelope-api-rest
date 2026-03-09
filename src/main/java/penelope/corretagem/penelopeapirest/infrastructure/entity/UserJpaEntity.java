package penelope.corretagem.penelopeapirest.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import penelope.corretagem.penelopeapirest.data.domain.entity.AppointmentEntity;
import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso")
    private AccessLevel accessLevel;

    @Column(name = "data_criacao")
    private LocalDate dateCreation;

    @Column(name = "ativo")
    private boolean active = true;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<AppointmentEntity> appointmentsClient;

    @OneToMany(
            mappedBy = "estateAgent",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<AppointmentEntity> appointmentsEstateAgent;

    @Column
    private String passwordResetToken;

    @Column
    private Date passwordResetTokenExpiry;
}
