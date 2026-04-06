package penelope.corretagem.penelopeapirest.core.eventType;

public class EventType {

    private final Long id;
    private final String title;
    private final String slug;

    private EventType(Long id, String title, String slug) {
        this.id = id;
        this.title = title;
        this.slug = slug;
    }

    public static EventType createNew(String title, String slug) {
        return new EventType(null, title, slug);
    }

    public static EventType restore(Long id, String title, String slug) {
        return new EventType(id, title, slug);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }
}
