//package penelope.corretagem.penelopeapirest.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import penelope.corretagem.penelopeapirest.service.WebhookService;
//
//@RestController
//@RequestMapping("/cal")
//public class WebhookController {
//  private final WebhookService webhookService;
//
//  public WebhookController(WebhookService webhookService) {
//    this.webhookService = webhookService;
//  }
//
//  @PostMapping()
//  public ResponseEntity<Void> handleCalWebhook(@RequestBody String rawBody, @RequestHeader("X-Cal-Signature-256") String signature) {
//
//    webhookService.processAppointment(rawBody, signature);
//
//    return ResponseEntity.ok().build();
//  }
//}
