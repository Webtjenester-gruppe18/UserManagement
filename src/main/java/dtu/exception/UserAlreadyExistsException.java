package dtu.exception;

/**
 * @author Frederik Hjorth s175109
 */

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
