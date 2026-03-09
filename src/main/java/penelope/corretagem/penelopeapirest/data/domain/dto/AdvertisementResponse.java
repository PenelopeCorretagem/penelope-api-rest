package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.application.dto.EstateResponse;
import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.CreatorResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.EventTypeResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.ResponsibleResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdvertisementResponse(
        Long id,
        Boolean active,
        Boolean emphasis,
        LocalDateTime createdAt,
        LocalDate endDate,
        CreatorResponse creator,
        ResponsibleResponse responsible,
        EstateResponse estate,
        EventTypeResponse eventTypeId
) {
    public static AdvertisementResponse fromDomain(Advertisement ad) {
        return new AdvertisementResponse(
                ad.getId(),
                ad.getActive(),
                ad.getEmphasis(),
                ad.getCreatedAt(),
                ad.getEndDate(),
                CreatorResponse.fromDomain(ad.getCreator()),
                ResponsibleResponse.fromDomain(ad.getResponsible()),
                EstateResponse.fromDomain(ad.getEstate()),
                EventTypeResponse.fromDomain(ad.getEventType())
        );
    }
}