package eventmanagement.kernel.core.domain.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HTTP status 409 – Event overlapping
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class OverlappingEventException extends GenericEventmanagementException {

    public OverlappingEventException(String msg, ErrorType errorType) {
        super(msg, errorType);
    }

}
