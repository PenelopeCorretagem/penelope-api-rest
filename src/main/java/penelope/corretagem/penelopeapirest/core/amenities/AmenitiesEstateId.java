package penelope.corretagem.penelopeapirest.core.amenities;

import java.io.Serializable;
import java.util.Objects;

public class AmenitiesEstateId implements Serializable {

    private Long estate;
    private Long amenity;

    public AmenitiesEstateId() {
    }

    public AmenitiesEstateId(Long estate, Long amenity) {
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
