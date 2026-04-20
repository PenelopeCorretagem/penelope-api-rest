package penelope.corretagem.penelopeapirest.application.useCase.advertisement;

import penelope.corretagem.penelopeapirest.core.advertisement.repository.IAdvertisementRepository;

public class DeleteAdvertisementUseCase {

    private final IAdvertisementRepository advertisementRepository;

    public DeleteAdvertisementUseCase(IAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public void execute(Long id) {
        advertisementRepository.deleteById(id);
    }
}
