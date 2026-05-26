package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import org.springframework.cache.annotation.Cacheable;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.config.CacheNames;
import penelope.corretagem.penelopeapirest.core.advertisement.AdvertisementFilter;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllAdvertisementsUseCase {

    private final IAdvertisementRepository repository;

    public GetAllAdvertisementsUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    @Cacheable(
            value = CacheNames.ADVERTISEMENTS,
            key = "#filter.city + ':' + #filter.region + ':' + #filter.type + ':' + #filter.numberOfRooms + ':' + #filter.active + ':' + #filter.area + ':' + #filter.title + ':' + #filter.description + ':' + #filter.createdAt + ':' + #filter.createdAtMin + ':' + #filter.createdAtMax"
    )
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
            filter.createdAtMax()
        );

        var advertisements = repository.findAll(domainFilter);

        return advertisements.stream()
                .map(AdvertisementResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
