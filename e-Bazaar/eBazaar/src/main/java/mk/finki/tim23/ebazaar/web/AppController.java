package mk.finki.tim23.ebazaar.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.tim23.ebazaar.models.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    @GetMapping({"", "/home"})
    public String getHome(HttpServletRequest request, Model model) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("user", request.getSession().getAttribute("user"));
        model.addAttribute("bodyContent", "index");
        return "master-template";
    }
}
