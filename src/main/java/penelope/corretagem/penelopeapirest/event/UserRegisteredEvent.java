package penelope.corretagem.penelopeapirest.event;

import org.springframework.context.ApplicationEvent;
import penelope.corretagem.penelopeapirest.entity.UserEntity;

public class UserRegisteredEvent extends ApplicationEvent {

    private final UserEntity registeredUser;

    /**
     * @param source O objeto que publicou o evento (geralmente 'this').
     * @param registeredUser A entidade do usuário que foi salva, para que os ouvintes possam usá-la.
     */
    public UserRegisteredEvent(Object source, UserEntity registeredUser) {
        super(source);
        this.registeredUser = registeredUser;
    }

    public UserEntity getRegisteredUser() {
        return registeredUser;
    }
}
