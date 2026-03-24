package penelope.corretagem.penelopeapirest.application.dto;

import java.util.List;

public record EstateCreateRequest(
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AddressRequest address,
        AddressRequest standAddress,
        List<Long> amenitiesIds,
        List<ImageRequest> images
) {
}