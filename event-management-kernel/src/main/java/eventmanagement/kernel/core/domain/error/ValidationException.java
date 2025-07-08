package eventmanagement.kernel.core.domain.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends GenericEventmanagementException {

    public ValidationException(String msg, ErrorType errorType) {
        super(msg, errorType);
    }

}