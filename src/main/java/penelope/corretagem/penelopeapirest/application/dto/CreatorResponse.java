package penelope.corretagem.penelopeapirest.application.dto;

import penelope.corretagem.penelopeapirest.core.user.User;

public record CreatorResponse(
        Long id,
        String name,
        String email,
        String cellphone
) {
    public static CreatorResponse fromDomain(User creator) {
        if (creator == null) return null;

        return new CreatorResponse(
                creator.getId(),
                creator.getName(),
                creator.getEmail(),
                creator.getPhone()
        );
    }
}
