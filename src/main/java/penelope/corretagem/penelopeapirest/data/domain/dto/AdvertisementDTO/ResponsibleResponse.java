package penelope.corretagem.penelopeapirest.data.domain.dto.AdvertisementDTO;

import penelope.corretagem.penelopeapirest.core.user.User;

public record ResponsibleResponse(
        Long id,
        String name,
        String email,
        String cellphone) {

    public static ResponsibleResponse fromDomain(User user) {
        if (user == null) return null;

        return new ResponsibleResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
