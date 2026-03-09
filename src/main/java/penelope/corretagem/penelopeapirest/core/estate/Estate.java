package penelope.corretagem.penelopeapirest.core.estate;

import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;

import java.util.Set;

public class Estate {
    private final Long id;
    private final String title;
    private final String description;
    private final Double area;
    private final Integer numberOfRooms;
    private final Type type;
    private final Address address;
    private final Address standAddress;
    private final Set<ImageEstate> images;
    private final Set<AmenitiesEstate> amenities;

    private Estate(
            Long id,
            String title,
            String description,
            Double area,
            Integer numberOfRooms,
            Type type,
            Address address,
            Address standAddress,
            Set<ImageEstate> images,
            Set<AmenitiesEstate> amenities
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.type = type;
        this.address = address;
        this.standAddress = standAddress;
        this.images = images;
        this.amenities = amenities;
    }

    public static Estate createNew(
            Long id,
            String title,
            String description,
            Double area,
            Integer numberOfRooms,
            Type type,
            Address address,
            Address standAddress,
            Set<ImageEstate> images,
            Set<AmenitiesEstate> amenities) {
        return new Estate(
                id,
                title,
                description,
                area,
                numberOfRooms,
                type,
                address,
                standAddress,
                images,
                amenities
        );
    }

    public static Estate restore(
            Long id,
            String title,
            String description,
            Double area,
            Integer numberOfRooms,
            Type type,
            Address address,
            Address standAddress,
            Set<ImageEstate> images,
            Set<AmenitiesEstate> amenities) {
        return new Estate(
                id,
                title,
                description,
                area,
                numberOfRooms,
                type,
                address,
                standAddress,
                images,
                amenities
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getArea() {
        return area;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Type getType() {
        return type;
    }

    public Address getAddress() {
        return address;
    }

    public Address getStandAddress() {
        return standAddress;
    }

    public enum Type {
        DISPONIVEL("Disponível"),
        EM_OBRAS("Em obras"),
        LANCAMENTO("Lançamento");
        private final String typeName;

        Type(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeName() {
            return typeName;
        }
    }

    public Set<ImageEstate> getImages() {
        return images;
    }

    public Set<AmenitiesEstate> getAmenities() {
        return amenities;
    }
}