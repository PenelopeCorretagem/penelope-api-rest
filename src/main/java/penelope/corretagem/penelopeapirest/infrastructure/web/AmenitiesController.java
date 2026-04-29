package penelope.corretagem.penelopeapirest.infrastructure.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.dto.AmenitiesResponse;
import penelope.corretagem.penelopeapirest.application.dto.CreateAmenityRequest;
import penelope.corretagem.penelopeapirest.application.dto.PaginatedAmenitiesResponse;
import penelope.corretagem.penelopeapirest.application.dto.UpdateAmenityRequest;
import penelope.corretagem.penelopeapirest.application.useCase.amenities.*;

@RestController
@RequestMapping("/v1/amenities")
public class AmenitiesController {

    private final GetAllAmenitiesUseCase getAllAmenitiesUseCase;
    private final GetAmenityByIdUseCase getAmenityByIdUseCase;
    private final CreateAmenityUseCase createAmenityUseCase;
    private final UpdateAmenityUseCase updateAmenityUseCase;
    private final DeleteAmenityUseCase deleteAmenityUseCase;

    public AmenitiesController(GetAllAmenitiesUseCase getAllAmenitiesUseCase, GetAmenityByIdUseCase getAmenityByIdUseCase, CreateAmenityUseCase createAmenityUseCase, UpdateAmenityUseCase updateAmenityUseCase, DeleteAmenityUseCase deleteAmenityUseCase) {
        this.getAllAmenitiesUseCase = getAllAmenitiesUseCase;
        this.getAmenityByIdUseCase = getAmenityByIdUseCase;
        this.createAmenityUseCase = createAmenityUseCase;
        this.updateAmenityUseCase = updateAmenityUseCase;
        this.deleteAmenityUseCase = deleteAmenityUseCase;
    }

    @GetMapping
    public ResponseEntity<PaginatedAmenitiesResponse> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String initial,
            @RequestParam(defaultValue = "ASC") String sort) {
        return ResponseEntity.ok(getAllAmenitiesUseCase.execute(page, pageSize, name, initial, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenitiesResponse> getAmenityById(@PathVariable Long id) {
        return ResponseEntity.ok(getAmenityByIdUseCase.execute(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AmenitiesResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateAmenityRequest request) {

        var updatedDomain = updateAmenityUseCase.execute(id, request);

        var response = AmenitiesResponse.fromDomain(updatedDomain);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateAmenityRequest request) {

        createAmenityUseCase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteAmenityUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}