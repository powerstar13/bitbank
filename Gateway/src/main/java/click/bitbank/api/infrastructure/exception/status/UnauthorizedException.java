package click.bitbank.api.infrastructure.exception.status;

import org.springframework.http.HttpStatus;
import click.bitbank.api.infrastructure.exception.GlobalException;

public class UnauthorizedException extends GlobalException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

    public UnauthorizedException(String reason, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, reason, cause);
    }
}
