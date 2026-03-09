package penelope.corretagem.penelopeapirest.data.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import penelope.corretagem.penelopeapirest.core.amenities.Amenities;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstateId;
import penelope.corretagem.penelopeapirest.core.estate.Estate;

@Entity
@Table(name = "diferencial_empreendimento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesEstateEntity {

    @EmbeddedId
    private AmenitiesEstateId id; // chave composta

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estate") // mapeia o campo estate da chave composta
    @JoinColumn(name = "fk_empreendimento", referencedColumnName = "id", nullable = false)
    private EstateEntity estate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("amenity") // mapeia o campo amenity da chave composta
    @JoinColumn(name = "fk_diferencial", referencedColumnName = "id", nullable = false)
    private AmenitiesEntity amenity;
}
