package eventmanagement.kernel.core.domain.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * HTTP status 404 – Entity not found
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAlreadyExistsException extends RuntimeException {

    private final ErrorType errorType;

    public UserAlreadyExistsException(String msg, ErrorType errorType) {
        super(msg);
        this.errorType = errorType;
    }

}
