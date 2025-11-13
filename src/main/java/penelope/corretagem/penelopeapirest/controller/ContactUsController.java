package penelope.corretagem.penelopeapirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import penelope.corretagem.penelopeapirest.data.domain.dto.ContactUsRequest;
import penelope.corretagem.penelopeapirest.service.EmailService;

@RestController
@RequestMapping("/contact-us")
public class ContactUsController {

    @Autowired
    EmailService emailService;

    @PostMapping
    public ResponseEntity<String> contactUs(@RequestBody ContactUsRequest contactUsRequest)  {
        emailService.contactUsEmail(contactUsRequest);
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }
}
