package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.application.useCase.images.UploadImagesUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/v2/imagens")
public class ImageUploadController {

    private final UploadImagesUseCase uploadImagesUseCase;

    public ImageUploadController(UploadImagesUseCase uploadImagesUseCase) {
        this.uploadImagesUseCase = uploadImagesUseCase;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {

        List<String> urls = uploadImagesUseCase.execute(files);

        return ResponseEntity.ok(urls);
    }
}