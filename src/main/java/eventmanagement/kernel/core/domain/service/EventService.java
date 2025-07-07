package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.error.EventNotFoundException;
import eventmanagement.kernel.core.domain.model.EventBO;

import java.util.List;

public interface EventService {

    List<EventBO> findAllEvents();
    EventBO findEventById(Long id) throws EventNotFoundException;
    EventBO createEvent(EventBO event) throws eventmanagement.kernel.core.domain.error.ValidationException;
    EventBO updateEvent(Long id, EventBO event) throws EventNotFoundException;
    void deleteEvent(Long id) throws EventNotFoundException;

}
