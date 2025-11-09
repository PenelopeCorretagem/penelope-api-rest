package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AddressResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.ImagesResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.StandAddressResponse;

import java.util.Set;

public record EstateResponse(
        Long id,
        String title,
        String description,
        Double area,
        Integer numberOfRooms,
        String type,
        AddressResponse address,
        StandAddressResponse addressStand,
        Set<ImagesResponse> images,
        Set<AmenitiesResponse> amenities
) {}
