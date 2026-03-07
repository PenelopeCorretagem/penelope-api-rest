package penelope.corretagem.penelopeapirest.core.advertisement;

import penelope.corretagem.penelopeapirest.core.estate.Estate;
import penelope.corretagem.penelopeapirest.core.user.User;
import penelope.corretagem.penelopeapirest.data.domain.entity.EventTypeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Advertisement {
    private Long id;
    private final Estate estate;
    private final User creator;
    private final LocalDateTime createdAt;

    private User responsible;
    private Boolean active;
    private Boolean emphasis;
    private LocalDate endDate;
    private EventTypeEntity eventType;

    private Advertisement(
            Long id,
            Estate estate,
            User creator,
            User responsible,
            Boolean active,
            Boolean emphasis,
            LocalDate endDate,
            LocalDateTime createdAt,
            EventTypeEntity eventType) {
        this.id = id;
        this.estate = estate;
        this.creator = creator;
        this.responsible = responsible;
        this.active = active;
        this.emphasis = emphasis;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.eventType = eventType;
    }

    // 1. Factory Method para criar um NOVO anúncio (regra de negócio)
    public static Advertisement createNew(
            Estate estate,
            User creator,
            User responsible,
            EventTypeEntity eventType,
            LocalDate endDate) {

        Boolean defaultActive = true;
        Boolean defaultEmphasis = false;
        LocalDateTime now = LocalDateTime.now();

        return new Advertisement(null, estate, creator, responsible, defaultActive, defaultEmphasis, endDate, now, eventType);
    }

    public static Advertisement restore(
            Long id,
            Estate estate,
            User creator,
            User responsible,
            Boolean active,
            Boolean emphasis,
            LocalDate endDate,
            LocalDateTime createdAt,
            EventTypeEntity eventType) {

        return new Advertisement(id, estate, creator, responsible, active, emphasis, endDate, createdAt, eventType);
    }

    public void deactivate() {
        if (!this.active) {
            throw new IllegalArgumentException("O anúncio já está inativo.");
        }
        this.active = false;
    }

    public void renewAdvertisement(LocalDate newEndDate) {
        if (newEndDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A nova data deve ser no futuro.");
        }
        this.endDate = newEndDate;
        this.active = true; // Renovar automaticamente reativa o anúncio
    }

    public void changeResponsible(User newResponsible) {
        if (newResponsible == null) {
            throw new IllegalArgumentException("O responsável não pode ser nulo.");
        }
        this.responsible = newResponsible;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public EventTypeEntity getEventType() {
        return eventType;
    }
}