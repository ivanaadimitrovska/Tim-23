package mk.finki.tim23.ebazaar.service;

import mk.finki.tim23.ebazaar.models.Category;
import mk.finki.tim23.ebazaar.models.Comment;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAllCommentsOnAPost(Long postId);

    Optional<Comment> save(String message, String commentedBy, Long postId);

    Optional<Comment> edit(Long id, String message);

    void deleteById(Long id);
}
