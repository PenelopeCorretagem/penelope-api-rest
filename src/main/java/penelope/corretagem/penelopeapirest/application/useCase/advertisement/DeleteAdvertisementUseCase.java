package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;
import penelope.corretagem.penelopeapirest.core.exception.ResourceNotFoundException;

public class DeleteAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;

    public DeleteAdvertisementUseCase(IAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public void execute(Long id) {
        if (!advertisementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Anúncio não encontrado");
        }

        advertisementRepository.deleteById(id);
    }
}
