package penelope.corretagem.penelopeapirest.core.amenities;

import java.util.Set;

public class Amenities {
    private Long id;
    private String description;
    private Set<AmenitiesEstate> properties = null;

    private Amenities(
            Long id,
            String description,
            Set<AmenitiesEstate> properties
    ) {
        this.id = id;
        this.description = description;
        this.properties = properties;
    }

    public Amenities(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Amenities createNew(Long id, String description, Set<AmenitiesEstate> amenitiesEstates) {
        return new Amenities(id, description, amenitiesEstates);
    }

    public static Amenities restore(Long id, String description, Set<AmenitiesEstate> amenitiesEstates) {
        return new Amenities(id, description, amenitiesEstates);
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<AmenitiesEstate> getProperties() {
        return properties;
    }
}
