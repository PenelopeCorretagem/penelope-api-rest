package penelope.corretagem.penelopeapirest.application.useCase.images;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.core.gateway.IImageStorageGateway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadImagesUseCase {

    private final IImageStorageGateway imageStorageGateway;

    public UploadImagesUseCase(IImageStorageGateway imageStorageGateway) {
        this.imageStorageGateway = imageStorageGateway;
    }

    public List<String> execute(List<MultipartFile> files) {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String url = imageStorageGateway.uploadImage(file.getBytes(), file.getOriginalFilename());
                uploadedUrls.add(url);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler o arquivo: " + file.getOriginalFilename(), e);
            }
        }

        return uploadedUrls;
    }
}