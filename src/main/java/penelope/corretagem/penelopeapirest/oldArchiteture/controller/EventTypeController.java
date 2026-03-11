//package penelope.corretagem.penelopeapirest.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;
//import penelope.corretagem.penelopeapirest.service.EventTypeService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/event-types")
//@CrossOrigin(origins = "*")
//@Tag(name = "Cal Tipos de Eventos", description = "Gerencia tipos de eventos do Cal")
//public class EventTypeController {
//
//    private final EventTypeService eventTypeService;
//
//    public EventTypeController(EventTypeService eventTypeService) {
//        this.eventTypeService = eventTypeService;
//    }
//
//    /**
//     * Cria um Event Type para um imóvel específico
//     */
//
//    @PostMapping("/estate/{estateId}")
//    @Operation(
//        summary = "Cria um Event Type para um imóvel específico",
//        description = "Cria um novo tipo de evento no Cal.com associado a um imóvel específico."
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Event Type criado com sucesso"),
//        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
//    public ResponseEntity<EventTypeCalResponse> createEventTypeForEstate(@PathVariable Long estateId) {
//        try {
//            EventTypeCalResponse response = eventTypeService.createEventTypeForEstate(estateId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    /**
//     * Atualiza um Event Type de um imóvel
//     */
//    @PutMapping("/estate/{estateId}")
//    @Operation(
//        summary = "Atualiza um Event Type de um imóvel",
//        description = "Atualiza o tipo de evento no Cal.com associado a um imóvel específico."
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Event Type atualizado com sucesso"),
//        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
//    public ResponseEntity<EventTypeCalResponse> updateEventTypeForEstate(@PathVariable Long estateId) {
//        try {
//            EventTypeCalResponse response = eventTypeService.updateEventTypeForEstate(estateId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    /**
//     * Lista todos os Event Types
//     */
//    @GetMapping
//    @Operation(
//        summary = "Lista todos os Event Types",
//        description = "Retorna uma lista de todos os tipos de eventos existentes no Cal.com."
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Lista de Event Types retornada com sucesso"),
//        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
//    public List<EventTypeCalResponse> listAllEventTypes() {
//        return eventTypeService.listAllEventTypes();
//    }
//
//    /**
//     * Busca um Event Type por ID
//     */
//    @GetMapping("/{eventTypeId}")
//    @Operation(
//        summary = "Busca um Tipo de Evento por ID",
//        description = "Retorna os detalhes de tipo de evento no Cal.com por ID."
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Tipo de evento retornado com sucesso"),
//        @ApiResponse(responseCode = "404", description = "Tipo de evento não encontrado"),
//        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
//    public ResponseEntity<EventTypeCalResponse> getEventType(@PathVariable Long eventTypeId) {
//        try {
//            EventTypeCalResponse response = eventTypeService.getEventType(eventTypeId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    /**
//     * Deleta um Tipo de evento de um imóvel
//     */
//    @DeleteMapping("/estate/{estateId}")
//    @Operation(
//        summary = "Deleta um Tipo de evento de um imóvel",
//        description = "Deleta o tipo de evento no Cal.com associado a um imóvel específico."
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Tipo de evento deletado com sucesso"),
//        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
//        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
//    })
//    public ResponseEntity<Void> deleteEventTypeForEstate(@PathVariable Long estateId) {
//        try {
//            eventTypeService.deleteEventTypeForEstate(estateId);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}