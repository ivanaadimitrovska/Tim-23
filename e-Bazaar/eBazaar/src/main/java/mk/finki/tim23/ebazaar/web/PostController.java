package mk.finki.tim23.ebazaar.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.tim23.ebazaar.models.Category;
import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String getAllPosts(HttpServletRequest request, Model model) {
        List<Post> posts = this.postService.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("user", request.getSession().getAttribute("user"));
        model.addAttribute("bodyContent", "post-view");
        return "master-template";
    }

    @GetMapping("/posts/{category}")
    public String getAllPostsByCategory(@PathVariable String category,
                                        Model model) {
        Category c = Category.valueOf(category);
        List<Post> posts = this.postService.findAllPostByCategory(c);
        model.addAttribute("posts", posts);
        model.addAttribute("category", c);
        model.addAttribute("bodyContent", "post-view");
        return "master-template";
    }

    @GetMapping("/edit/{postId}")
    public String editPost(@PathVariable Long postId, Model model) {
        if (this.postService.findById(postId).isPresent()) {
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
                             @RequestParam byte[] image,
                             @RequestParam Category category) {
        this.postService.edit(postId, title, price, description, image, category);
        return "redirect:/categories";
    }

    @PostMapping("/add")
    public String addPost(@RequestParam String title,
                          @RequestParam Double price,
                          @RequestParam String description,
                          @RequestParam("image") MultipartFile imageFile,
                          @RequestParam Category category,
                          HttpServletRequest request) {
        byte[] imageBytes;
        try {
            imageBytes = imageFile.getBytes();
        } catch (IOException e) {
            // Handle exception
            return "redirect:/";
        }
        this.postService.save(title, price, description, imageBytes, category, request);
        return "redirect:/home";
    }

    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        this.postService.deleteById(postId);
        return "redirect:/categories";
    }

    @GetMapping("/form")
    public String getForm(HttpServletRequest request, Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("bodyContent", "form");
        model.addAttribute("user", request.getSession().getAttribute("user"));
        return "master-template";
    }
}

