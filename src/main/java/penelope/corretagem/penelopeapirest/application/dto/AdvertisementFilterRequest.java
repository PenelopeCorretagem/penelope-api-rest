package penelope.corretagem.penelopeapirest.application.dto;

import java.time.LocalDate;

public record AdvertisementFilterRequest(
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
