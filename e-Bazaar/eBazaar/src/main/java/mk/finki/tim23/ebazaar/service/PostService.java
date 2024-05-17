package mk.finki.tim23.ebazaar.service;

import mk.finki.tim23.ebazaar.models.Category;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();

    Optional<Post> findById(Long id);

    List<Post> findAllPostByCategory(Category category);

    Optional<Post> findByTitle(String title);

    Optional<Post> save(String title, Double price, String description, Byte[] image, Category category);

    Optional<Post> edit(Long id, String title, Double price, String description, Byte[] image, Category category);

    void deleteById(Long id);
}
