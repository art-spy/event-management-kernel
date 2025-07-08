package eventmanagement.kernel.core.domain.error;

import eventmanagement.kernel.core.domain.model.UserBO;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HTTP status 409 – Event overlapping
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class OverlappingEventException extends GenericEventmanagementException {

    UserBO user;

    public OverlappingEventException(String msg, ErrorType errorType, UserBO user) {
        super(msg, errorType);
        this.user = user;
    }

}
