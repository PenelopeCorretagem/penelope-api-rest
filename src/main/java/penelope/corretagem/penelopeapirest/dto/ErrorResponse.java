package penelope.corretagem.penelopeapirest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Modelo de resposta de erro")
public class ErrorResponse {

    @Schema(description = "Código HTTP do erro", example = "400")
    private int status;

    @Schema(description = "Mensagem de erro", example = "Dados inválidos")
    private String message;

    @Schema(description = "Detalhes adicionais do erro", example = "[\"O campo email é obrigatório\"]")
    private List<String> details;

    @Schema(description = "Timestamp do erro", example = "2025-10-05T18:30:00Z")
    private Instant timestamp;

    // Getters e setters
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<String> getDetails() { return details; }
    public void setDetails(List<String> details) { this.details = details; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
