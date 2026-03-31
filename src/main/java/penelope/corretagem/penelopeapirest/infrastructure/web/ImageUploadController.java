package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.application.useCase.images.UploadImageCommand;
import penelope.corretagem.penelopeapirest.application.useCase.images.UploadImagesUseCase;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/imagens")
public class ImageUploadController {

    private final UploadImagesUseCase uploadImagesUseCase;

    public ImageUploadController(UploadImagesUseCase uploadImagesUseCase) {
        this.uploadImagesUseCase = uploadImagesUseCase;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {

        var commands = files.stream()
                .map(file -> {
                    try {
                        return new UploadImageCommand(file.getBytes(), file.getOriginalFilename());
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Não foi possível ler um dos arquivos enviados", e);
                    }
                })
                .toList();

        List<String> urls = uploadImagesUseCase.execute(commands);

        return ResponseEntity.ok(urls);
    }
}