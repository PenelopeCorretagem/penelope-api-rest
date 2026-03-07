package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_evento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeEntity {

    @Id
    private Long id;

    @Column(name = "titulo",unique = true)
    private String title;

    @Column(unique = true)
    private String slug;
}
