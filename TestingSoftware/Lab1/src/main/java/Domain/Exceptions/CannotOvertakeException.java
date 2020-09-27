package Domain.Exceptions;

public class CannotOvertakeException extends RuntimeException {
    public CannotOvertakeException(String message) {
        super(message);
    }
}
