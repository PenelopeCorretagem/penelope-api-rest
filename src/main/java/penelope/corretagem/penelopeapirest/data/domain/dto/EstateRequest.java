package penelope.corretagem.penelopeapirest.data.domain.dto;

import java.util.Set;

public record EstateRequest(
        Long id,
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AddressRequest address,
        AddressRequest addressStand,
        Set<ImageEstateRequest> images
) {
}