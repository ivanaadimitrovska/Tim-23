package mk.finki.tim23.ebazaar.repository;

import mk.finki.tim23.ebazaar.models.Comment;
import mk.finki.tim23.ebazaar.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
