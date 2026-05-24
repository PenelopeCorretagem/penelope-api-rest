package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;

import java.time.LocalDateTime;

public record AdvertisementResponse(
        Long id,
        Boolean active,
        Boolean emphasis,
        LocalDateTime createdAt,
        CreatorResponse creator,
        ResponsibleResponse responsible,
        EstateResponse estate
) {
    public static AdvertisementResponse fromDomain(Advertisement ad) {
        return new AdvertisementResponse(
                ad.getId(),
                ad.getActive(),
                ad.getEmphasis(),
                ad.getCreatedAt(),
                CreatorResponse.fromDomain(ad.getCreator()),
                ResponsibleResponse.fromDomain(ad.getResponsible()),
                EstateResponse.fromDomain(ad.getEstate())
        );
    }
}
