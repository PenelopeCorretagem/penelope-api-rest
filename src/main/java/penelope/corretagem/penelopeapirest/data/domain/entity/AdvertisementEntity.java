package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    @JoinColumn(name = "fk_criador", referencedColumnName = "id", nullable = false)
    private UserEntity creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_responsavel", referencedColumnName = "id", nullable = false)
    private UserEntity responsible;

    @Column(name = "ativo", nullable = false)
    private boolean active;

    @Column(name = "destaque", nullable = false)
    private boolean emphasis;

    @Column(name = "data_inicio")
    private LocalDate startDate;

    @Column(name = "data_fim", nullable = false)
    private LocalDate endDate;
}
