package penelope.corretagem.penelopeapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO.AdvertisementFilterRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementResponse;
import penelope.corretagem.penelopeapirest.service.AdvertisementService;

import java.util.List;

@RestController
@RequestMapping("/anuncios")
public class AdvertisementController {

    private final AdvertisementService service;

    public AdvertisementController(AdvertisementService service) {
        this.service = service;
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

}
