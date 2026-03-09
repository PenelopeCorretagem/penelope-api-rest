package penelope.corretagem.penelopeapirest.infrastructure.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventTypeJpaEntity {

    @Id
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String slug;
}

