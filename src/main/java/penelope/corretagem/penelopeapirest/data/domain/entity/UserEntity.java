package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import penelope.corretagem.penelopeapirest.data.domain.enums.AccessLevel;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso", nullable = false)
    private AccessLevel accessLevel;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dateCreation;

    @Column(name = "ativo", nullable = false)
    private boolean active;

    @Column
    private String passwordResetToken;

    @Column
    private Date passwordResetTokenExpiry;
}
