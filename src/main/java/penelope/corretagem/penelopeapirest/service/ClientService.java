package penelope.corretagem.penelopeapirest.service;

import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import penelope.corretagem.penelopeapirest.entity.ClientEntity;
import penelope.corretagem.penelopeapirest.event.UserRegisteredEvent;
import penelope.corretagem.penelopeapirest.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @EventListener
    @Transactional
    public void handleUserRegistration(UserRegisteredEvent event){

        ClientEntity newClient = new ClientEntity();
        newClient.setNome(event.getRegisteredUser().getNomeCompleto());
        newClient.setUsuario(event.getRegisteredUser());

        clientRepository.save(newClient);
    }
}
