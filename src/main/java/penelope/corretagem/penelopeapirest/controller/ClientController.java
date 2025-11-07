package penelope.corretagem.penelopeapirest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import penelope.corretagem.penelopeapirest.data.domain.dto.ClientRequest;
import penelope.corretagem.penelopeapirest.data.domain.dto.ClientResponse;
import penelope.corretagem.penelopeapirest.service.ClientService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

  private final ClientService service;

  public ClientController(ClientService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<ClientResponse>> getClients() {
    return ResponseEntity.ok(service.getAllClients());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponse> getClientById(@PathVariable @NotNull Long id) {
    return ResponseEntity.ok(service.getClientById(id));
  }

  @PostMapping
  public ResponseEntity<ClientResponse> createClient(
    @RequestBody @Valid ClientRequest request,
    UriComponentsBuilder uriBuilder) {

    ClientResponse response = service.createClient(request);

    URI location = uriBuilder.path("/client/{id}")
      .buildAndExpand(response.id())
      .toUri();

    return ResponseEntity.created(location).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientResponse> updateClient(
    @PathVariable Long id,
    @RequestBody @Valid ClientRequest request) {

    ClientResponse response = service.updateClient(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ClientResponse> deleteClient(@PathVariable Long id) {
    service.deleteClient(id);
    return ResponseEntity.noContent().build();
  }
}
