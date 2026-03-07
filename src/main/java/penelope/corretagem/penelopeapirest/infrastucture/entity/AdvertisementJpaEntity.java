package penelope.corretagem.penelopeapirest.infrastucture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// Lembre-se de ajustar os imports abaixo para apontar para as suas classes JPA Entity reais
// import penelope.corretagem.penelopeapirest.infrastucture.entity.EstateEntity;
// import penelope.corretagem.penelopeapirest.infrastucture.entity.UserEntity;
// import penelope.corretagem.penelopeapirest.infrastucture.entity.EventTypeEntity;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_empreendimento")
//    private EstateEntity estate; // No seu repositório antigo também era chamado de property em algumas queries
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_criador")
//    private UserEntity creator;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_responsavel")
//    private UserEntity responsible;
//
//    @Column(name = "ativo")
//    private Boolean active;
//
//    @Column(name = "destaque") // Supondo o nome da coluna no banco
//    private Boolean emphasis;
//
//    @Column(name = "data_fim")
//    private LocalDate endDate;
//
//    @Column(name = "data_criacao") // Supondo o nome da coluna no banco
//    private LocalDateTime createdAt;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_tipo_evento_cal")
//    private EventTypeEntity eventType;
}