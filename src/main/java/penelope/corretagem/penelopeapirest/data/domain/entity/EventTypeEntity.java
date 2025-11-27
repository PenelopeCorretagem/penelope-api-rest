package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventTypeEntity {

    @Id
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(unique = true)
    private String slug;
}
