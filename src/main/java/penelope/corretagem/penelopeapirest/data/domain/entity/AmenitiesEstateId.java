package penelope.corretagem.penelopeapirest.data.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmenitiesEstateId implements Serializable {

    private Long estate;
    private Long amenity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmenitiesEstateId)) return false;
        AmenitiesEstateId that = (AmenitiesEstateId) o;
        return Objects.equals(estate, that.estate) &&
                Objects.equals(amenity, that.amenity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estate, amenity);
    }
}
