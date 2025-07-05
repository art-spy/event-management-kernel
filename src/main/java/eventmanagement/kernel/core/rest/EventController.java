package eventmanagement.kernel.core.rest;

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

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<EventDto> getAll() {
        return eventService.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(eventService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) throws ValidationException {
        return ResponseEntity.ok(
                mapper.toDto(eventService.create(mapper.toBo(dto)))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> update(
            @PathVariable Long id,
            @RequestBody EventDto dto
    ) throws ValidationException {
        return ResponseEntity.ok(
                mapper.toDto(eventService.update(id, mapper.toBo(dto)))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
