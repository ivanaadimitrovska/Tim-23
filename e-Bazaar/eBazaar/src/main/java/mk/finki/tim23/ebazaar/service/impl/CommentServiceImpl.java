package mk.finki.tim23.ebazaar.service.impl;

import mk.finki.tim23.ebazaar.models.Comment;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.CommentNotFoundException;
import mk.finki.tim23.ebazaar.models.exceptions.PostNotFoundException;
import mk.finki.tim23.ebazaar.models.exceptions.UserNotFoundException;
import mk.finki.tim23.ebazaar.repository.CommentRepository;
import mk.finki.tim23.ebazaar.repository.PostRepository;
import mk.finki.tim23.ebazaar.repository.UserRepository;
import mk.finki.tim23.ebazaar.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> findAllCommentsOnAPost(Long postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow( () -> new PostNotFoundException());
        List<Comment> comments = this.commentRepository.findAllByPost(post);
        return comments;
    }

    @Override
    public Optional<Comment> save(String message, String commentedBy, Long postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        User user = this.userRepository.findByUsername(commentedBy)
                .orElseThrow( () -> new UserNotFoundException(commentedBy));
        Comment comment = new Comment(message,user,post);
        this.commentRepository.save(comment);
        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> edit(Long id, String message) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow( () -> new CommentNotFoundException());
        comment.setMessage(message);
        return Optional.of(comment);
    }

    @Override
    public void deleteById(Long id) {
        this.commentRepository.deleteById(id);
    }
}
