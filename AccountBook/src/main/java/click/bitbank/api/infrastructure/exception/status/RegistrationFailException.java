package click.bitbank.api.infrastructure.exception.status;

import click.bitbank.api.infrastructure.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class RegistrationFailException extends GlobalException {

    public RegistrationFailException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public RegistrationFailException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }

    public RegistrationFailException(String reason, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
    }
}
