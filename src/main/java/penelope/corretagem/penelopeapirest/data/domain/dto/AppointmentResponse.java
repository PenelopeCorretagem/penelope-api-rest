package penelope.corretagem.penelopeapirest.data.domain.dto;

import org.springframework.web.reactive.function.client.ClientResponse;

public record AppointmentResponse(
    long id,
    String date,
    String time,
    ClientResponse client,
    String serviceType
) {
}
