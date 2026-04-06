package penelope.corretagem.penelopeapirest.infrastructure.api.dto;

public record CalUser(
        Long id,
        String username,
        String email,
        String name
) {
}
