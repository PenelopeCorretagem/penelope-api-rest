package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.CreatorResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.ResponsibleResponse;

import java.time.LocalDate;

public record AdvertisementResponse(
        Long id,
        boolean active,
        boolean emphasis,
        LocalDate startDate,
        LocalDate endDate,
        CreatorResponse creator,
        ResponsibleResponse responsible,
        EstateResponse property
) {
}
