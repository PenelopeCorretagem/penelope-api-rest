package penelope.corretagem.penelopeapirest.data.domain.dto;

import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.CreatorResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.EventTypeResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.ResponsibleResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;

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
        EstateResponse property,
        EventTypeResponse eventTypeId
) {
}
