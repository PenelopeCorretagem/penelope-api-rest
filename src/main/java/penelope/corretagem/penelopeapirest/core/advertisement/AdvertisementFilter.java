package penelope.corretagem.penelopeapirest.core.advertisement;

import java.time.LocalDate;

public record AdvertisementFilter(
        String city,
        String region,
        String type,
        Integer numberOfRooms,
        Boolean active,
        Double area,
        String title,
        String description,
        LocalDate createdAt,
        LocalDate createdAtMin,
        LocalDate createdAtMax
) {
}
