package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.core.estate.Estate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "anuncio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ativo")
    private Boolean active;

    @Column(name = "destaque")
    private Boolean emphasis;

    @Column(name = "data_criacao")
    private LocalDateTime createdAt;

    @Column(name = "data_fim")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "fk_criador", referencedColumnName = "id")
    private UserEntity creator;

    @ManyToOne
    @JoinColumn(name = "fk_responsavel", referencedColumnName = "id")
    private UserEntity responsible;

    @ManyToOne
    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id")
    private EstateEntity estate;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_evento", referencedColumnName = "id")
    private EventTypeEntity eventType;

}