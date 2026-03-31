package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.core.advertisement.AdvertisementFilter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAdvertisementsUseCase {

    private final IAdvertisementRepository repository;

    public GetAllAdvertisementsUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public List<AdvertisementResponse> execute(AdvertisementFilterRequest filter) {

        var domainFilter = new AdvertisementFilter(
            filter.city(),
            filter.region(),
            filter.type(),
            filter.numberOfRooms(),
            filter.active(),
            filter.area(),
            filter.title(),
            filter.description(),
            filter.createdAt(),
            filter.createdAtMin(),
            filter.createdAtMax(),
            filter.endDate(),
            filter.endDateMin(),
            filter.endDateMax()
        );

        var advertisements = repository.findAll(domainFilter);

        return advertisements.stream()
                .map(AdvertisementResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
