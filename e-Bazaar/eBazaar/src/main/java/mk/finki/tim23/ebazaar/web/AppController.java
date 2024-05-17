package mk.finki.tim23.ebazaar.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class AppController {

    @GetMapping()
    public String getPage(){
        return "index";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }
}