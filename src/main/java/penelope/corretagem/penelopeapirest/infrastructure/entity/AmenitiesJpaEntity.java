package penelope.corretagem.penelopeapirest.infrastructure.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity
@Table(name = "diferencial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true)
    private String description;

    @Column(name = "icon_name")
    private String icon;

    @OneToMany(
            mappedBy = "amenity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<AmenitiesEstateJpaEntity> properties;
}

