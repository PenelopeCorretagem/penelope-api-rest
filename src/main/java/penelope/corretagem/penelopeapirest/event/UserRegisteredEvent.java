package penelope.corretagem.penelopeapirest.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.data.domain.entity.UserEntity;

@Getter
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
}