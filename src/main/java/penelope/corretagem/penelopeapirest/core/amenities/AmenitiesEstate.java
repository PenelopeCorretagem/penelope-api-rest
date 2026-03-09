package penelope.corretagem.penelopeapirest.core.amenities;

import penelope.corretagem.penelopeapirest.core.estate.Estate;

public class AmenitiesEstate {
    private AmenitiesEstateId id;
    private Estate estate;
    private Amenities amenity;

    private AmenitiesEstate(AmenitiesEstateId id, Estate estate, Amenities amenity) {
        this.id = id;
        this.estate = estate;
        this.amenity = amenity;
    }

    public static AmenitiesEstate createNew(AmenitiesEstateId amenitiesEstateId, Estate estate, Amenities amenity){
        return new AmenitiesEstate(amenitiesEstateId, estate,amenity);
    }

    public static AmenitiesEstate restore(AmenitiesEstateId amenitiesEstateId, Estate estate, Amenities amenity){
        return new AmenitiesEstate(amenitiesEstateId, estate,amenity);
    }

    public AmenitiesEstateId getId() {
        return id;
    }

    public Estate getEstate() {
        return estate;
    }

    public Amenities getAmenity() {
        return amenity;
    }
}
