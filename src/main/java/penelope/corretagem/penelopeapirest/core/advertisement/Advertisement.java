package penelope.corretagem.penelopeapirest.core.advertisement;

import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.exception.DomainValidationException;
import penelope.corretagem.penelopeapirest.core.user.User;

import java.time.LocalDateTime;

public class Advertisement {
    private Long id;
    private final Estate estate;
    private final User creator;
    private final LocalDateTime createdAt;

    private User responsible;
    private Boolean active;
    private Boolean emphasis;

    private Advertisement(
            Long id,
            Estate estate,
            User creator,
            User responsible,
            Boolean active,
            Boolean emphasis,
            LocalDateTime createdAt) {
        this.id = id;
        this.estate = estate;
        this.creator = creator;
        this.responsible = responsible;
        this.active = active;
        this.emphasis = emphasis;
        this.createdAt = createdAt;
    }

    public static Advertisement createNew(
            Estate estate,
            User creator,
            User responsible) {

        Boolean defaultActive = true;
        Boolean defaultEmphasis = false;
        LocalDateTime now = LocalDateTime.now();

        return new Advertisement(null, estate, creator, responsible, defaultActive, defaultEmphasis, now);
    }

    public static Advertisement restore(
            Long id,
            Estate estate,
            User creator,
            User responsible,
            Boolean active,
            Boolean emphasis,
            LocalDateTime createdAt) {

        return new Advertisement(id, estate, creator, responsible, active, emphasis, createdAt);
    }

    //Getters
    public Long getId() {
        return id;
    }

    public Estate getEstate() {
        return estate;
    }

    public User getCreator() {
        return creator;
    }

    public User getResponsible() {
        return responsible;
    }

    public Boolean getActive() {
        return active;
    }

    public Boolean getEmphasis() {
        return emphasis;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateInfo(Boolean active) {
        this.active = active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setEmphasis(Boolean emphasis) {
        this.emphasis = emphasis;
    }

    public void updateResponsible(User responsible) {
        if (responsible == null) {
            throw new DomainValidationException("Responsável é obrigatório");
        }

        this.responsible = responsible;
    }
}
