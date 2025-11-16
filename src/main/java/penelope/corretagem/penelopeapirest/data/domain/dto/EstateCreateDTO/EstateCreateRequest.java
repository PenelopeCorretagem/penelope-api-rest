package penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO;

import penelope.corretagem.penelopeapirest.data.domain.dto.AddressRequest;
import penelope.corretagem.penelopeapirest.data.domain.entity.EstateEntity;

import java.util.List;
import java.util.Set;

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

        List<ImageUploadRequest> images
) {}
