//package penelope.corretagem.penelopeapirest.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import penelope.corretagem.penelopeapirest.service.WebhookService;
//
//@RestController
//@RequestMapping("/cal")
//@Tag(name = "Cal Webhook", description = "Gerencia webhooks do Cal")
//public class WebhookController {
//  private final WebhookService webhookService;
//
//  public WebhookController(WebhookService webhookService) {
//    this.webhookService = webhookService;
//  }
//
//  @Operation(
//    summary = "Recebe webhooks do Cal.com para processar agendamentos",
//    description = "Endpoint para receber webhooks do Cal.com quando um agendamento é criado/atualizado/cancelado.")
//  @ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "Webhook processado com sucesso"),
//    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//    @ApiResponse(responseCode = "401", description = "Assinatura inválida"),
//    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//  })
//  @PostMapping()
//  public ResponseEntity<Void> handleCalWebhook(@RequestBody String rawBody, @RequestHeader("X-Cal-Signature-256") String signature) {
//
//    webhookService.processAppointment(rawBody, signature);
//
//    return ResponseEntity.ok().build();
//  }
//}