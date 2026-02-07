package penelope.corretagem.penelopeapirest.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.data.domain.entity.LogEntity;
import penelope.corretagem.penelopeapirest.event.UserRegisteredEvent;
import penelope.corretagem.penelopeapirest.data.domain.repository.LogRepository;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @EventListener
    public void handleUserRegistration(UserRegisteredEvent event) {
        System.out.println("Ouvinte de Log: Recebeu evento de novo usuário. Salvando log...");

        LogEntity newLog = new LogEntity();
        newLog.setUsuario(event.getRegisteredUser());
        newLog.setOrigem("CADASTRO_USUARIO");
        newLog.setDetalhes("Novo usuário cadastrado com o e-mail: " + event.getRegisteredUser().getEmail());
        logRepository.save(newLog);

        System.out.println("Log de cadastro de usuário salvo com sucesso.");
    }
}
