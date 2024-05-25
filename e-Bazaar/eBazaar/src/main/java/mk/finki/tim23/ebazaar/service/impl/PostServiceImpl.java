package mk.finki.tim23.ebazaar.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.tim23.ebazaar.models.Category;
import mk.finki.tim23.ebazaar.models.Comment;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.PostNotFoundException;
import mk.finki.tim23.ebazaar.models.exceptions.UserNotFoundException;
import mk.finki.tim23.ebazaar.repository.CommentRepository;
import mk.finki.tim23.ebazaar.repository.PostRepository;
import mk.finki.tim23.ebazaar.repository.UserRepository;
import mk.finki.tim23.ebazaar.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public List<Post> findAllPostByCategory(Category category) {
        return this.postRepository.findAllByCategory(category);
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return this.postRepository.findByTitle(title);
    }

    @Override
    public Optional<Post> save(String title, Double price, String description,
                               byte[] image, Category category, HttpServletRequest request, String location, String city) {
        User user = (User) request.getSession().getAttribute("user");
        Post post = new Post(title, price, description, image, category, user, location, city);
        this.postRepository.save(post);
        return Optional.of(post);
    }

    @Override
    public Optional<Post> edit(Long id, String title, Double price, String description, byte[] image, Category category, String location, String city) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.setTitle(title);
        post.setPrice(price);
        post.setDescription(description);
        if (location != null) post.setLocation(location);
        if (city != null) post.setCity(city);
        post.setCategory(category);
        if (image.length != 0) {
            post.setImage(image);
        }
        this.postRepository.save(post);
        return Optional.of(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }


    @Override
    public Optional<Post> addComment(Long postId, String message, User commentedBy) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        Comment comment = new Comment(message, commentedBy, post);
        this.commentRepository.save(comment);

        return Optional.of(post);
    }
}