package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.data.domain.dto.AddressRequest;
import java.util.List;

public record EstateCreateRequest(
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AdvertisementCreateRequest advertisementCreateRequest,
        AddressRequest address,
        AddressRequest standAddress,
        List<Long> amenitiesIds,
        List<String> images,
        List<Integer> imageType
) {}
