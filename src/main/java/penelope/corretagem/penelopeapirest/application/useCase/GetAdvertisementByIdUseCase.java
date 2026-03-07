package penelope.corretagem.penelopeapirest.application.useCase;

import penelope.corretagem.penelopeapirest.core.advertisement.Advertisement;
import penelope.corretagem.penelopeapirest.core.advertisement.repository.AdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;

public class GetAdvertisementByIdUseCase {

    private final AdvertisementRepository repository;

    public GetAdvertisementByIdUseCase(AdvertisementRepository repository) {
        this.repository = repository;
    }

    public AdvertisementResponse execute(Long id){
        Advertisement advertisement = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio não encontrado com o ID: " + id));

        return AdvertisementResponse.fromDomain(advertisement);
    }
}
