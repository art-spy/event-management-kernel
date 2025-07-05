package eventmanagement.kernel.core.domain.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HTTP status 409 – Event overlapping
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class OverlappingEventException extends RuntimeException {

    public OverlappingEventException(String msg) {
        super(msg);
    }

}
