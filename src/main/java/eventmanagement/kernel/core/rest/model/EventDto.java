package eventmanagement.kernel.core.rest.model;

import eventmanagement.kernel.core.domain.model.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String title;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private EventType type;
    private Set<UserDto> participants;

}
