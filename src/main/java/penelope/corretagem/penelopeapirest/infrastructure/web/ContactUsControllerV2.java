package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.useCase.email.SendContactEmailUseCase;
import penelope.corretagem.penelopeapirest.oldArchiteture.domain.dto.ContactUsRequest;

@RestController
@RequestMapping("/api/v2/contact-us")
public class ContactUsControllerV2 {

    private final SendContactEmailUseCase sendContactEmailUseCase;

    public ContactUsControllerV2(SendContactEmailUseCase sendContactEmailUseCase) {
        this.sendContactEmailUseCase = sendContactEmailUseCase;
    }

    @PostMapping
    public ResponseEntity<String> contactUs(@RequestBody ContactUsRequest contactUsRequest) {
        sendContactEmailUseCase.execute(contactUsRequest);
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }
}