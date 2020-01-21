package dtu.exception;

/**
 * @author Frederik Hjorth s175109
 */

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }
}
