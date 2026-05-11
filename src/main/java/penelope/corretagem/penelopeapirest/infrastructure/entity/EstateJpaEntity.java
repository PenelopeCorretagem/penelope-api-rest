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
    @Convert(converter = penelope.corretagem.penelopeapirest.infrastructure.entity.converter.EstateTypeAttributeConverter.class)
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
        DISPONIVEL(3, "Disponível"),
        EM_OBRAS(1, "Em obras"),
        LANCAMENTO(2, "Lançamento");

        private final int code;
        private final String typeName;

        Type(int code, String typeName) {
            this.code = code;
            this.typeName = typeName;
        }

        public int getCode() {
            return code;
        }

        public static Type fromCode(int code) {
            for (Type value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Codigo de tipo de imovel invalido: " + code);
        }
    }
}

