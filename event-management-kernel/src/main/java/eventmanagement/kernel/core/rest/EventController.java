package eventmanagement.kernel.core.rest;

import eventmanagement.kernel.api.EventEndpoint;
import eventmanagement.kernel.api.model.EventDto;
import eventmanagement.kernel.core.domain.error.GenericEventmanagementException;
import eventmanagement.kernel.core.domain.error.OverlappingEventException;
import eventmanagement.kernel.core.domain.model.EventBO;
import eventmanagement.kernel.core.domain.service.EventService;
import eventmanagement.kernel.core.rest.mapper.BoDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
 * CRUD endpoint controller for /api/events
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController implements EventEndpoint {

    private final EventService eventService;
    private final BoDtoMapper mapper;

    @GetMapping
    @Override
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventBO> eventBOList = eventService.findAllEvents();
        List<EventDto> eventDTOList = new ArrayList<>();

        for (EventBO eventBO : eventBOList) {
            EventDto eventDTO = mapper.toDto(eventBO);
            eventDTOList.add(eventDTO);
        }

        return ResponseEntity.ok(eventDTOList);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mapper.toDto(eventService.findEventById(id)));
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    @PostMapping
    @Override
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto dto) {
        try {
            return ResponseEntity.ok(
                    mapper.toDto(eventService.createEvent(mapper.toBo(dto))));
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<EventDto> updateEvent(Long id, EventDto dto) {
        try {
            return ResponseEntity.ok(
                    mapper.toDto(eventService.updateEvent(id, mapper.toBo(dto))));
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<EventDto> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (GenericEventmanagementException e) {
            return generateErrorResponse(e);
        }
    }

    private ResponseEntity<EventDto> generateErrorResponse(GenericEventmanagementException e) {
        EventDto errorDto = new EventDto();
        errorDto.getMessages().put("errorType", e.getErrorType());
        if (e instanceof OverlappingEventException) {
            errorDto.getMessages().put("user", ((OverlappingEventException) e).getUser());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

}
