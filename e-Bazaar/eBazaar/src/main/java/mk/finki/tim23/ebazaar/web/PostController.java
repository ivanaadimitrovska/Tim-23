package mk.finki.tim23.ebazaar.web;

import mk.finki.tim23.ebazaar.models.Category;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String getAllPosts() {
        return "categories";
    }

    @GetMapping("/posts/{category}")
    public String getAllPostsByCategory(@PathVariable String category,
                                        Model model) {
        Category c = Category.valueOf(category);
        List<Post> posts = this.postService.findAllPostByCategory(c);
        model.addAttribute("posts", posts);
        model.addAttribute("category", c);
        return "post-view";
    }

    @GetMapping("/edit/{postId}")
    public String editPost(@PathVariable Long postId, Model model) {
        if(this.postService.findById(postId).isPresent()){
            Post post = this.postService.findById(postId).get();
            List<Category> categories = List.of(Category.values());
            model.addAttribute("categories", categories);
            model.addAttribute("post", post);
            return "categories";
        }
        return "redirect:/index.html";
    }

    @PostMapping("/{postId}")
    public String updatePost(@RequestParam Long postId,
                             @RequestParam String title,
                             @RequestParam Double price,
                             @RequestParam String description,
                             @RequestParam Byte[] image,
                             @RequestParam Category category) {
        this.postService.edit(postId,title,price,description,image,category);
        return "redirect:/categories";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String title,
                          @RequestParam Double price,
                          @RequestParam String description,
                          @RequestParam Byte[] image,
                          @RequestParam Category category){
        this.postService.save(title,price,description,image,category);
        return "redirect:/categories";
    }

    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        this.postService.deleteById(postId);
        return "redirect:/categories";
    }
}
