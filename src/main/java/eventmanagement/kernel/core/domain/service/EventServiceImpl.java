package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.error.OverlappingEventException;
import eventmanagement.kernel.core.domain.model.EventBO;
import eventmanagement.kernel.core.domain.model.EventType;
import eventmanagement.kernel.core.domain.model.UserBO;
import eventmanagement.kernel.core.persistance.UserEntity;
import eventmanagement.kernel.core.persistance.mapper.EntityBoMapper;
import eventmanagement.kernel.core.persistance.repository.EventJpaRepository;
import eventmanagement.kernel.core.persistance.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventJpaRepository eventJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final EntityBoMapper entityBoMapper;

    @Override
    public List<EventBO> findAll() {
        return eventJpaRepository.findAll().stream()
                .map(entityBoMapper::toBO)
                .sorted((a,b) -> a.getStartDateTime().compareTo(b.getStartDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public EventBO findById(Long id) {
        return eventJpaRepository.findById(id)
                .map(entityBoMapper::toBO)
                .orElseThrow(() -> new EntityNotFoundException("Event nicht gefunden: " + id));
    }

    @Override
    @Transactional
    public EventBO create(EventBO event) throws ValidationException {
        validateDuration(event);
        var participants = fetchAndValidateParticipants(event.getParticipants());
        event.setParticipants(participants);
        checkOverlap(event);
        return entityBoMapper.toBO(eventJpaRepository.save(entityBoMapper.toEntity(event)));
    }

    @Override
    @Transactional
    public EventBO update(Long id, EventBO event) throws ValidationException {
        if (!eventJpaRepository.existsById(id)) {
            throw new EntityNotFoundException("Event nicht gefunden: " + id);
        }
        event.setId(id);
        validateDuration(event);
        var participants = fetchAndValidateParticipants(event.getParticipants());
        event.setParticipants(participants);
        checkOverlap(event);
        return entityBoMapper.toBO(eventJpaRepository.save(entityBoMapper.toEntity(event)));
    }

    @Override
    public void delete(Long id) {
        if (!eventJpaRepository.existsById(id)) {
            throw new EntityNotFoundException("Event nicht gefunden: " + id);
        }
        eventJpaRepository.deleteById(id);
    }

    /** Dauer-Regeln prüfen */
    private void validateDuration(EventBO event) throws ValidationException {
        var duration = Duration.between(event.getStartDateTime(), event.getEndDateTime());
        if (event.getStartDateTime().isAfter(event.getEndDateTime())) {
            throw new ValidationException("Enddatum vor Startdatum.");
        }
        boolean max24h = duration.toHours() > 24;
        if ((event.getType() == EventType.AFTERWORK
                || event.getType() == EventType.FESTIVITY)
                && duration.toHours() > 24) {
            throw new ValidationException("Afterwork/Fest darf max. 24 h dauern.");
        }
    }

    /** Teilnehmer laden */
    private Set<UserBO> fetchAndValidateParticipants(Set<UserBO> bos) {
        return bos.stream().map(uBO -> {
            UserEntity entity = null;
            try {
                entity = userJpaRepository.findById(uBO.getId())
                        .orElseThrow(() -> new ValidationException("Teilnehmer nicht gefunden: " + uBO.getId()));
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            return entityBoMapper.toBO(entity);
        }).collect(Collectors.toSet());
    }

    /** Überschneidungen prüfen */
    private void checkOverlap(EventBO event) {
        for (UserBO user : event.getParticipants()) {
            var overlaps = eventJpaRepository.findEventOverlapping(
                    user.getId(), event.getStartDateTime(), event.getEndDateTime()
            );
            boolean conflict = overlaps.stream()
                    .anyMatch(x -> !x.getId().equals(event.getId()));
            if (conflict) {
                throw new OverlappingEventException(
                        "Benutzer " + user.getEmail() + " hat zeitlich überlappendes Event."
                );
            }
        }
    }
}
