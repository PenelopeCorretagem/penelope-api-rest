package penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO;

import java.util.Date;

public record AdvertisementCreateRequest(
        Long creator,
        Long responsible,
        Date dataFim,
        Boolean active
        ) {}
