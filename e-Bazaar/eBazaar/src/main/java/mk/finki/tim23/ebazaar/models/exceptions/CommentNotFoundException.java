package mk.finki.tim23.ebazaar.models.exceptions;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        super("Comment not found.");
    }
}
