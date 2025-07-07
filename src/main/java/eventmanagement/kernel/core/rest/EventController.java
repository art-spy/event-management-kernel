package eventmanagement.kernel.core.rest;

import eventmanagement.kernel.core.domain.model.EventBO;
import eventmanagement.kernel.core.domain.service.EventService;
import eventmanagement.kernel.core.rest.mapper.BoDtoMapper;
import eventmanagement.kernel.core.rest.model.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * CRUD endpoints for /api/events
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final BoDtoMapper mapper;

    @GetMapping
    public List<EventDto> getAllEvents() {
        List<EventBO> eventBOList = eventService.findAllEvents();
        List<EventDto> eventDTOList = new ArrayList<>();

        for (EventBO eventBO : eventBOList) {
            EventDto eventDTO = mapper.toDto(eventBO);
            eventDTOList.add(eventDTO);
        }

        return eventDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(eventService.findEventById(id)));
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto dto) {
        return ResponseEntity.ok(
                mapper.toDto(eventService.createEvent(mapper.toBo(dto)))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto dto) {
        return ResponseEntity.ok(
                mapper.toDto(eventService.updateEvent(id, mapper.toBo(dto)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
