package penelope.corretagem.penelopeapirest.infrastructure.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AmenitiesEstateJpaId implements Serializable {

    private Long estate;
    private Long amenity;

    public AmenitiesEstateJpaId() {
    }

    public AmenitiesEstateJpaId(Long estate, Long amenity) {
        this.estate = estate;
        this.amenity = amenity;
    }

    public Long getEstate() {
        return estate;
    }

    public void setEstate(Long estate) {
        this.estate = estate;
    }

    public Long getAmenity() {
        return amenity;
    }

    public void setAmenity(Long amenity) {
        this.amenity = amenity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmenitiesEstateJpaId that)) return false;
        return Objects.equals(estate, that.estate) && Objects.equals(amenity, that.amenity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estate, amenity);
    }
}
