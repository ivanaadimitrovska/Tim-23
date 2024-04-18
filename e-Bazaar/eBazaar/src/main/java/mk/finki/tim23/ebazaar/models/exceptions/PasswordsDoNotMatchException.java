package mk.finki.tim23.ebazaar.models.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException() {
        super("Passwords do not match!");
    }
}
