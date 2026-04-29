package penelope.corretagem.penelopeapirest.application.useCase.images;

import penelope.corretagem.penelopeapirest.core.exception.InvalidImagePayloadException;
import penelope.corretagem.penelopeapirest.core.gateway.IImageStorageGateway;
import java.util.ArrayList;
import java.util.List;

public class UploadImagesUseCase {

    private final IImageStorageGateway imageStorageGateway;

    public UploadImagesUseCase(IImageStorageGateway imageStorageGateway) {
        this.imageStorageGateway = imageStorageGateway;
    }

    public List<String> execute(List<UploadImageCommand> files) {
        List<String> uploadedUrls = new ArrayList<>();

        for (UploadImageCommand file : files) {
            if (file.content() == null || file.originalFilename() == null) {
                throw new InvalidImagePayloadException("desconhecido", null);
            }

            String url = imageStorageGateway.uploadImage(file.content(), file.originalFilename());
            uploadedUrls.add(url);
        }

        return uploadedUrls;
    }
}