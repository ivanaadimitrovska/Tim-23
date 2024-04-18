package mk.finki.tim23.ebazaar.models.exceptions;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException() {
        super(String.format("Post not found."));
    }
}
