package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;

public class GetLatestAdvertisementUseCase {

    private final IAdvertisementRepository repository;

    public GetLatestAdvertisementUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public AdvertisementResponse execute(boolean canViewInactive) {
        return repository.findLatest(canViewInactive)
                .map(AdvertisementResponse::fromDomain)
                .orElse(null);
    }
}
