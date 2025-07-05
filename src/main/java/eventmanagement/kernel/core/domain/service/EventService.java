package eventmanagement.kernel.core.domain.service;

import eventmanagement.kernel.core.domain.model.EventBO;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface EventService {

    List<EventBO> findAll();
    EventBO findById(Long id);
    EventBO create(EventBO event) throws ValidationException;
    EventBO update(Long id, EventBO event) throws ValidationException;
    void delete(Long id);

}
