package api_code.exception;

public class BloqueadoException extends ExceptionPai {

    private final long retryAfterSeconds;

    public BloqueadoException(String message) {
        super(message);
        this.retryAfterSeconds = 0; 

    }

    public BloqueadoException(String message, long retryAfterSeconds) {
        super(message);
        this.retryAfterSeconds = retryAfterSeconds;
    }

    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }

}
