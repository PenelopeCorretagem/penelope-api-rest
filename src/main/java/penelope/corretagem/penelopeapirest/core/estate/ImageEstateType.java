package penelope.corretagem.penelopeapirest.core.estate;

import java.util.Collections;
import java.util.Set;

public class ImageEstateType {

    private final Long id;
    private final String description;

    private final Set<ImageEstate> images;

    private ImageEstateType(Long id, String description, Set<ImageEstate> images) {
        this.id = id;
        this.description = description;
        this.images = images != null ? images : Collections.emptySet();
    }

    public static ImageEstateType createNew(String description, Set<ImageEstate> images) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("A descrição do tipo de imagem não pode estar vazia.");
        }
        return new ImageEstateType(null, description, images);
    }

    public static ImageEstateType restore(Long id, String description, Set<ImageEstate> images) {
        return new ImageEstateType(id, description, images);
    }

    // Getters manuais
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<ImageEstate> getImages() {
        return Collections.unmodifiableSet(images);
    }
}
