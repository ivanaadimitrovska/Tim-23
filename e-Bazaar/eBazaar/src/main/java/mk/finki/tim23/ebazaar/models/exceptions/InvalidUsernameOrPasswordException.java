package mk.finki.tim23.ebazaar.models.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException {

    public InvalidUsernameOrPasswordException() {
        super("Invalid user credentials");
    }
}
