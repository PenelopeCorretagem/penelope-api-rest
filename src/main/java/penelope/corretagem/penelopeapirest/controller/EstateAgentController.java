package penelope.corretagem.penelopeapirest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.EstateAgentResponse;
import penelope.corretagem.penelopeapirest.service.EstateAgentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estate-agents")
public class EstateAgentController {

  private final EstateAgentService estateAgentService;

  public EstateAgentController(EstateAgentService estateAgentService) {
    this.estateAgentService = estateAgentService;
  }

  @GetMapping
  public ResponseEntity<List<EstateAgentResponse>> getEstateAgents() {
    return ResponseEntity.ok(estateAgentService.getAllEstateAgents());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EstateAgentResponse> getEstateAgentById(@PathVariable @NotNull Long id) {
    return ResponseEntity.ok(estateAgentService.getEstateAgentById(id));
  }

  @PostMapping
  public ResponseEntity<EstateAgentResponse> createEstateAgent(
      @RequestBody @Valid EstateAgentRequest estateAgent,
      UriComponentsBuilder uriBuilder) {

    EstateAgentResponse createdEstateAgent = estateAgentService.createEstateAgent(estateAgent);

    URI location = uriBuilder.path("/estate-agents/{id}")
      .buildAndExpand(createdEstateAgent.id())
      .toUri();

    return ResponseEntity.created(location).body(createdEstateAgent);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EstateAgentResponse> updateEstateAgent(
      @PathVariable Long id,
      @RequestBody @Valid EstateAgentRequest estateAgent) {

    EstateAgentResponse updatedEstateAgent = estateAgentService.updateEstateAgent(id, estateAgent);
    return ResponseEntity.ok(updatedEstateAgent);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<EstateAgentResponse> deleteEstateAgent(@PathVariable Long id) {
    estateAgentService.deleteEstateAgent(id);
    return ResponseEntity.noContent().build();
  }
}
