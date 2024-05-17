package mk.finki.tim23.ebazaar.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.tim23.ebazaar.models.Category;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.PostNotFoundException;
import mk.finki.tim23.ebazaar.models.exceptions.UserNotFoundException;
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

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
                               byte[] image, Category category, HttpServletRequest request) {
        //TODO: get the postAuthorUsername
        User user = (User) request.getSession().getAttribute("user");
        Post post = new Post(title,price,description,image,category,user);
        this.postRepository.save(post);
        return Optional.of(post);
    }

    @Override
    public Optional<Post> edit(Long id, String title, Double price, String description, byte[] image, Category category){
        Post post = this.postRepository.findById(id)
                .orElseThrow( () -> new PostNotFoundException());
        post.setTitle(title);
        post.setPrice(price);
        post.setDescription(description);
        post.setImage(image);
        post.setCategory(category);
        this.postRepository.save(post);
        return Optional.of(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }
}
