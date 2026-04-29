package penelope.corretagem.penelopeapirest.core.amenities;

import java.util.Set;

public class Amenities {
    private Long id;
    private String description;
    private String icon;
    private Set<AmenitiesEstate> properties = null;

    private Amenities(
            Long id,
            String description,
            String icon,
            Set<AmenitiesEstate> properties
    ) {
        this.id = id;
        this.description = description;
        this.icon = icon;
        this.properties = properties;
    }

    public static Amenities createNew(String description, String icon) {
        return new Amenities(null, description, icon, null);
    }

    public static Amenities restore(Long id, String description, String icon, Set<AmenitiesEstate> amenitiesEstates) {
        return new Amenities(id, description, icon, amenitiesEstates);
    }

    public void updateInfo(String description, String icon) {
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
        if (icon != null && !icon.isBlank()) {
            this.icon = icon;
        }
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

    public String getIcon() {
        return icon;
    }
}
