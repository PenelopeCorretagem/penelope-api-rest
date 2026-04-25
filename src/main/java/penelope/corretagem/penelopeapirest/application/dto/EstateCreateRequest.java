package penelope.corretagem.penelopeapirest.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EstateCreateRequest(
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AddressRequest address,
        List<Long> amenitiesIds,
        List<ImageRequest> images
) {
}