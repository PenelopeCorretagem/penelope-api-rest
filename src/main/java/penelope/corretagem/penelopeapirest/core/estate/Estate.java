package penelope.corretagem.penelopeapirest.core.estate;

import penelope.corretagem.penelopeapirest.core.address.Address;
import penelope.corretagem.penelopeapirest.core.amenities.AmenitiesEstate;

import java.util.Locale;
import java.util.Set;

public class Estate {
    private final Long id;
    private String title;
    private String description;
    private Double area;
    private Integer numberOfRooms;
    private Type type;
    private Address address;
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

    public enum Type {
        DISPONIVEL(3, "Disponível", "disponivel"),
        EM_OBRAS(1, "Em obras", "emObras"),
        LANCAMENTO(2, "Lançamento", "lancamento");

        private final int code;
        private final String typeName;
        private final String externalValue;

        Type(int code, String typeName, String externalValue) {
            this.code = code;
            this.typeName = typeName;
            this.externalValue = externalValue;
        }

        public int getCode() {
            return code;
        }

        public String getTypeName() {
            return typeName;
        }

        public String getDisplayName() {
            return typeName;
        }

        public String toExternalValue() {
            return externalValue;
        }

        public static Type fromCode(int code) {
            for (Type value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Codigo de tipo de imovel invalido: " + code);
        }

        public static Type fromExternalValue(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Tipo de imovel invalido: null");
            }

            String normalized = value.trim().toLowerCase(Locale.ROOT);
            if (normalized.isEmpty()) {
                throw new IllegalArgumentException("Tipo de imovel invalido: " + value);
            }

            for (Type type : values()) {
                if (type.externalValue.toLowerCase(Locale.ROOT).equals(normalized)
                        || type.name().toLowerCase(Locale.ROOT).equals(normalized)) {
                    return type;
                }
            }

            throw new IllegalArgumentException("Tipo de imovel invalido: " + value);
        }
    }

    public Set<ImageEstate> getImages() {
        return images;
    }

    public Set<AmenitiesEstate> getAmenities() {
        return amenities;
    }

    public void updateAllDetails(String title, String description, Double area, Integer numberOfRooms, Type type,
                                 Address address, Set<ImageEstate> images, Set<AmenitiesEstate> amenities) {
        this.title = title;
        this.description = description;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.type = type;
        this.address = address;

        this.images.clear();
        if (images != null) this.images.addAll(images);

        this.amenities.clear();
        if (amenities != null) this.amenities.addAll(amenities);
    }
}
