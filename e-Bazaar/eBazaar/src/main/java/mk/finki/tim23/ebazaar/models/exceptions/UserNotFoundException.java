package mk.finki.tim23.ebazaar.models.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String username) {
        super(String.format("User with username: %s not found", username));
    }
}
