package penelope.corretagem.penelopeapirest.application.useCase;

import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.dto.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAdvertisementsUseCase {

    private final IAdvertisementRepository repository;

    public GetAllAdvertisementsUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public List<AdvertisementResponse> execute(AdvertisementFilterRequest filter) {

        var advertisements = repository.findAll(filter);

        return advertisements.stream()
                .map(AdvertisementResponse::fromDomain)
                .collect(Collectors.toList());
    }
}
