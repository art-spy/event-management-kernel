package eventmanagement.kernel.core.domain.error;

import lombok.Getter;

/**
 * Generic exception for eventmanagement.
 * E.g., Used in controller methods to return error messages.
 * */
@Getter
public abstract class GenericEventmanagementException extends RuntimeException {

    private final ErrorType errorType;

    public GenericEventmanagementException(String msg, ErrorType errorType) {
        super(msg);
        this.errorType = errorType;
    }

}
