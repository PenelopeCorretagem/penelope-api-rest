package penelope.corretagem.penelopeapirest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.cal.eventtype.EventTypeCalResponse;
import penelope.corretagem.penelopeapirest.service.EventTypeService;

import java.util.List;

@RestController
@RequestMapping("/event-types")
@CrossOrigin(origins = "*")
public class EventTypeController {

    private final EventTypeService eventTypeService;

    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    /**
     * Cria um Event Type para um imóvel específico
     */
    @PostMapping("/estate/{estateId}")
    public ResponseEntity<EventTypeCalResponse> createEventTypeForEstate(@PathVariable Long estateId) {
        try {
            EventTypeCalResponse response = eventTypeService.createEventTypeForEstate(estateId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualiza um Event Type de um imóvel
     */
    @PutMapping("/estate/{estateId}")
    public ResponseEntity<EventTypeCalResponse> updateEventTypeForEstate(@PathVariable Long estateId) {
        try {
            EventTypeCalResponse response = eventTypeService.updateEventTypeForEstate(estateId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lista todos os Event Types
     */
    @GetMapping
    public List<EventTypeCalResponse> listAllEventTypes() {
        return eventTypeService.listAllEventTypes();
    }

    /**
     * Busca um Event Type específico
     */
    @GetMapping("/{eventTypeId}")
    public ResponseEntity<EventTypeCalResponse> getEventType(@PathVariable Long eventTypeId) {
        try {
            EventTypeCalResponse response = eventTypeService.getEventType(eventTypeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um Event Type de um imóvel
     */
    @DeleteMapping("/estate/{estateId}")
    public ResponseEntity<Void> deleteEventTypeForEstate(@PathVariable Long estateId) {
        try {
            eventTypeService.deleteEventTypeForEstate(estateId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}