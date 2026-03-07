package penelope.corretagem.penelopeapirest.core.estate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imagem_empreendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageEstate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id", nullable = false)
    private Estate estate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_imagem", referencedColumnName = "id", nullable = false)
    private ImageEstateType type;

    @Column(name = "url", nullable = false)
    private String url;
}