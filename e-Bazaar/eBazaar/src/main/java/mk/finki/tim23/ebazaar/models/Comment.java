package mk.finki.tim23.ebazaar.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    @ManyToOne
    private User commentedBy;

    @ManyToOne
    private Post post;

    public Comment() {}

    public Comment(String message, User commentedBy, Post post) {
        this.message = message;
        this.commentedBy = commentedBy;
        this.post = post;
    }
}
