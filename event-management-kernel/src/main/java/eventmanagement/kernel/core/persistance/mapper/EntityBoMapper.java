package eventmanagement.kernel.core.persistance.mapper;

import eventmanagement.kernel.core.domain.model.EventBO;
import eventmanagement.kernel.core.domain.model.UserBO;
import eventmanagement.kernel.core.persistance.EventEntity;
import eventmanagement.kernel.core.persistance.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface EntityBoMapper {

    UserBO toBO(UserEntity entity);
    UserEntity toEntity(UserBO bo);

    EventBO toBO(EventEntity entity);
    EventEntity toEntity(EventBO bo);

}
