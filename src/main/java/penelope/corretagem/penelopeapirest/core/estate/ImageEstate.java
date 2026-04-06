package penelope.corretagem.penelopeapirest.core.estate;

public class ImageEstate {

    private final Long id;
    private final Estate estate;
    private final ImageEstateType type;
    private final String url;

    private ImageEstate(Long id, Estate estate, ImageEstateType type, String url) {
        this.id = id;
        this.estate = estate;
        this.type = type;
        this.url = url;
    }

    public static ImageEstate createNew(Estate estate, ImageEstateType type, String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("A URL da imagem não pode estar vazia.");
        }
        return new ImageEstate(null, estate, type, url);
    }

    public static ImageEstate restore(Long id, Estate estate, ImageEstateType type, String url) {
        return new ImageEstate(id, estate, type, url);
    }

    public Long getId() { return id; }
    public Estate getEstate() { return estate; }
    public ImageEstateType getType() { return type; }
    public String getUrl() { return url; }
}