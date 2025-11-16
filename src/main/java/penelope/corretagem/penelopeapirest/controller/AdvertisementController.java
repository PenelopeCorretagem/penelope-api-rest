package penelope.corretagem.penelopeapirest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateCreateDTO.EstateCreateRequest;
import penelope.corretagem.penelopeapirest.data.domain.entity.AdvertisementEntity;
import penelope.corretagem.penelopeapirest.service.AdvertisementComposerService;
import penelope.corretagem.penelopeapirest.service.AdvertisementService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anuncios")
public class AdvertisementController {

    private final AdvertisementService service;
    private final AdvertisementComposerService composerService;

    public AdvertisementController(AdvertisementService service, AdvertisementComposerService composerService) {
        this.service = service;
        this.composerService = composerService;
    }

    @GetMapping
    public List<AdvertisementResponse> listAllActive(
            @ModelAttribute AdvertisementFilterRequest request) {
        return service.getAllActiveAdvertisements(request);
    }

    @GetMapping("/latest")
    public ResponseEntity<AdvertisementResponse> getLatestAdvertisement() {
        AdvertisementResponse latest = service.getLatestAdvertisement();
        if (latest != null) {
            return ResponseEntity.ok(latest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Long id) {
        var response = service.getAdvertisementById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Optional<AdvertisementEntity>> create(
            @RequestBody EstateCreateRequest request) throws IOException {
        Optional<AdvertisementEntity> response = composerService.createAdvertisement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/fotos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadImages(
            @RequestPart("files") List<MultipartFile> files) throws IOException {
        List<String> result = composerService.uploadImages(files);
        return ResponseEntity.ok(result);
    }
}
