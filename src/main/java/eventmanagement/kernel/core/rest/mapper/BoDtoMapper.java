package eventmanagement.kernel.core.rest.mapper;

import eventmanagement.kernel.core.domain.model.EventBO;
import eventmanagement.kernel.core.domain.model.UserBO;
import eventmanagement.kernel.core.rest.model.EventDto;
import eventmanagement.kernel.core.rest.model.UserDto;
import org.mapstruct.Mapper;

@Mapper
public interface BoDtoMapper {

    UserDto toDto(UserBO bo);
    UserBO toBo(UserDto dto);

    EventDto toDto(EventBO bo);
    EventBO toBo(EventDto dto);

}
