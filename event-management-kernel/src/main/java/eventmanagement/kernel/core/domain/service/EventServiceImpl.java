package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.api.model.EventType;
import eventmanagement.kernel.core.domain.error.ErrorType;
import eventmanagement.kernel.core.domain.error.EventNotFoundException;
import eventmanagement.kernel.core.domain.error.OverlappingEventException;
import eventmanagement.kernel.core.domain.error.UserNotFoundException;
import eventmanagement.kernel.core.domain.error.ValidationException;
import eventmanagement.kernel.core.domain.model.EventBO;
import eventmanagement.kernel.core.domain.model.UserBO;
import eventmanagement.kernel.core.persistance.EventEntity;
import eventmanagement.kernel.core.persistance.UserEntity;
import eventmanagement.kernel.core.persistance.mapper.EntityBoMapper;
import eventmanagement.kernel.core.persistance.repository.EventJpaRepository;
import eventmanagement.kernel.core.persistance.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventJpaRepository eventJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final EntityBoMapper entityBoMapper;

    @Override
    public List<EventBO> findAllEvents() {
        List<EventEntity> eventEntities = eventJpaRepository.findAll();
        List<EventBO> eventBOList = new ArrayList<>();

        for (EventEntity eventEntity : eventEntities) {
            EventBO eventBO = entityBoMapper.toBO(eventEntity);
            eventBOList.add(eventBO);
        }

        Collections.sort(eventBOList, Comparator.comparing(EventBO::getStartDate));

        return eventBOList;
    }

    @Override
    public EventBO findEventById(Long id) throws EventNotFoundException {
        return eventJpaRepository.findById(id)
                .map(entityBoMapper::toBO)
                .orElseThrow(() -> new EventNotFoundException("Event nicht gefunden: " + id, ErrorType.EVENT_NOT_FOUND));
    }

    @Override
    @Transactional
    public EventBO createEvent(EventBO event) throws ValidationException {
        validateEventDuration(event);
        var participants = retrieveParticipants(event.getParticipants());
        event.setParticipants(participants);
        checkOverlap(event);
        return entityBoMapper.toBO(eventJpaRepository.save(entityBoMapper.toEntity(event)));
    }

    @Override
    @Transactional
    public EventBO updateEvent(Long id, EventBO event) {
        if (!eventJpaRepository.existsById(id)) {
            throw new EventNotFoundException("Event nicht gefunden: " + id, ErrorType.EVENT_NOT_FOUND);
        }
        event.setId(id);
        validateEventDuration(event);
        var participants = retrieveParticipants(event.getParticipants());
        event.setParticipants(participants);
        checkOverlap(event);
        return entityBoMapper.toBO(eventJpaRepository.save(entityBoMapper.toEntity(event)));
    }

    @Override
    public void deleteEvent(Long id) throws EventNotFoundException {
        if (!eventJpaRepository.existsById(id)) {
            throw new EventNotFoundException("Event nicht gefunden: " + id, ErrorType.EVENT_NOT_FOUND);
        }
        eventJpaRepository.deleteById(id);
    }

    /**
     * Dauer-Regeln prüfen
     */
    private void validateEventDuration(EventBO event) throws ValidationException {
        var duration = Duration.between(event.getStartDate(), event.getEndDate());
        if (event.getStartDate().isAfter(event.getEndDate())) {
            throw new ValidationException("Enddatum vor Startdatum.", ErrorType.START_DATE_AFTER_END_DATE);
        }
        boolean max24h = duration.toHours() > 24;
        if ((event.getType() == EventType.AFTERWORK
                || event.getType() == EventType.FESTIVITY)
                && duration.toHours() > 24) {
            throw new ValidationException("Afterwork/Fest darf max. 24 h dauern.", ErrorType.EVENT_DURATION_EXCEEDED);
        }
    }

    /**
     * Teilnehmer laden
     */
    private Set<UserBO> retrieveParticipants(Set<UserBO> users) {
        Set<UserBO> result = new HashSet<>();
        
        for (UserBO user : users) {
            UserEntity entity = userJpaRepository.findById(user.getId())
                    .orElseThrow(() -> 
                            new UserNotFoundException("Teilnehmer nicht gefunden: " + user.getId(), ErrorType.USER_NOT_FOUND));
            result.add(entityBoMapper.toBO(entity));
        }
        
        return result;
    }

    /**
     * Überschneidungen prüfen
     */
    private void checkOverlap(EventBO event) {
        for (UserBO user : event.getParticipants()) {
            var overlaps = eventJpaRepository.findEventOverlapping(
                    user.getId(), event.getStartDate(), event.getEndDate()
            );
            boolean conflict = overlaps.stream()
                    .anyMatch(x -> !x.getId().equals(event.getId()));
            if (conflict) {
                throw new OverlappingEventException(
                        "Benutzer " + user.getEmail() + " hat zeitlich überlappendes Event.",
                        ErrorType.DATE_OVERLAPPING_USER,
                        user
                );
            }
        }
    }
}