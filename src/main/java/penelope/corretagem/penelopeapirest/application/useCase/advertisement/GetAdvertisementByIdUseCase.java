package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.application.dto.AdvertisementResponse;

public class GetAdvertisementByIdUseCase {

    private final IAdvertisementRepository repository;

    public GetAdvertisementByIdUseCase(IAdvertisementRepository repository) {
        this.repository = repository;
    }

    public AdvertisementResponse execute(Long id, boolean isAdministrator){
        Advertisement advertisement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio não encontrado com o ID: " + id));

        if (!Boolean.TRUE.equals(advertisement.getActive()) && !isAdministrator) {
            throw new ResourceNotFoundException("Anúncio não encontrado");
        }

        return AdvertisementResponse.fromDomain(advertisement);
    }
}
