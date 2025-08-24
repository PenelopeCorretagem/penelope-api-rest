package penelope.corretagem.penelopeapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-connection")
public class TestConnectionApiController {

  @GetMapping
  public ResponseEntity<String> testConnection() {
    return ResponseEntity.ok("Conexão bem sucedida!");
  }
}
