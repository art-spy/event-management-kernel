package eventmanagement.kernel.api;

import eventmanagement.kernel.api.model.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * CRUD endpoints for /api/events
 */
@RequestMapping("/api/events")
//@Tag(name = "/api/users")
//@FeignClient(name = "User Endpoint", url = "${kernel.url}")
public interface EventEndpoint {

    @GetMapping
    ResponseEntity<List<EventDto>> getAllEvents();

    @GetMapping("/{id}")
    ResponseEntity<EventDto> getEventById(@PathVariable Long id);

    @PostMapping
    ResponseEntity<EventDto> createEvent(@RequestBody EventDto dto);

    @PutMapping("/{id}")
    ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto dto);

    @DeleteMapping("/{id}")
    ResponseEntity<EventDto> deleteEvent(@PathVariable Long id);
}

