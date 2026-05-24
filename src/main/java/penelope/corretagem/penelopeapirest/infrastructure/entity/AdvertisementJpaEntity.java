package penelope.corretagem.penelopeapirest.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "anuncio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_empreendimento")
    private EstateJpaEntity estate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_criador")
    private UserJpaEntity creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_responsavel")
    private UserJpaEntity responsible;

    @Column(name = "ativo")
    private Boolean active;

    @Column(name = "destaque")
    private Boolean emphasis;

    @Column(name = "data_criacao")
    private LocalDateTime createdAt;
}
