package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "diferencial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, unique = true)
    private String description;
}
