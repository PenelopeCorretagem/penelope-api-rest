package penelope.corretagem.penelopeapirest.oldArchiteture.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientMustNotPossessACreci extends RuntimeException {

    public ClientMustNotPossessACreci(String message) {
        super(message);
    }
}
