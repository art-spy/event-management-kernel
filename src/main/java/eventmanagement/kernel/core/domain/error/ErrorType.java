package eventmanagement.kernel.core.domain.error;

public enum ErrorType {

    DATE_OVERLAPPING_USER,
    DATE_OVERLAPPING_EVENT,
    USER_NOT_FOUND,
    EVENT_NOT_FOUND,
    USER_ALREADY_EXISTS,
    START_DATE_AFTER_END_DATE,
    EVENT_DURATION_EXCEEDED,
    VALIDATION_ERROR

}
