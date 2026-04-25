package penelope.corretagem.penelopeapirest.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "empreendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstateJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, unique = true)
    private String title;

    @Column(name = "descricao", length = 150,nullable = false)
    private String description;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "quartos", nullable = false)
    private Integer numberOfRooms;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco", referencedColumnName = "id", nullable = false)
    private AddressJpaEntity address;

    @OneToMany(
            mappedBy = "estate",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<ImageEstateJpaEntity> images;

    @OneToMany(
            mappedBy = "estate",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<AmenitiesEstateJpaEntity> amenities;

    @Getter
    public enum Type {
        DISPONIVEL("Disponível"),
        EM_OBRAS("Em obras"),
        LANCAMENTO("Lançamento");

        private final String typeName;

        Type(String typeName) {
            this.typeName = typeName;
        }
    }
}

