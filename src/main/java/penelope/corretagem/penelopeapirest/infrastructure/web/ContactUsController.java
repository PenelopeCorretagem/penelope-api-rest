package penelope.corretagem.penelopeapirest.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.application.dto.ContactUsRequest;
import penelope.corretagem.penelopeapirest.application.useCase.email.SendContactEmailUseCase;

@RestController
@RequestMapping("/v1/contact-us")
public class ContactUsController {

    private final SendContactEmailUseCase sendContactEmailUseCase;

    public ContactUsController(SendContactEmailUseCase sendContactEmailUseCase) {
        this.sendContactEmailUseCase = sendContactEmailUseCase;
    }

    @PostMapping
    public ResponseEntity<String> contactUs(@RequestBody ContactUsRequest contactUsRequest) {
        sendContactEmailUseCase.execute(contactUsRequest);
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }
}