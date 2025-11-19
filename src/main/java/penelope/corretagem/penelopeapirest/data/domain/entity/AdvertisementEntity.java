package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anuncio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id", nullable = false)
    private EstateEntity property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_criador", referencedColumnName = "id", nullable = false)
    private UserEntity creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_responsavel", referencedColumnName = "id", nullable = false)
    private UserEntity responsible;

    @Column(name = "ativo", nullable = false)
    private Boolean active;

    @Column(name = "destaque", nullable = true)
    private Boolean emphasis;

    @Column(name = "data_fim", nullable = false)
    private LocalDate endDate;

    @Column(name = "data_criacao")
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_evento_cal", referencedColumnName = "id", nullable = false)
    private EventTypeEntity eventType;
}
