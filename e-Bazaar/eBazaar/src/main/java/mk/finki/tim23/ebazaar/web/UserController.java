package mk.finki.tim23.ebazaar.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUserCredentialsException;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUsernameOrPasswordException;
import mk.finki.tim23.ebazaar.models.exceptions.PasswordsDoNotMatchException;
import mk.finki.tim23.ebazaar.models.exceptions.UserNotFoundException;
import mk.finki.tim23.ebazaar.service.AuthService;
import mk.finki.tim23.ebazaar.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           @RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String phoneNumber) {
        try {
            this.userService.register(username, password, confirmPassword, email, fullName, phoneNumber);
            //TODO: Change the return
            return "redirect:/user/login";
        } catch (InvalidUserCredentialsException | PasswordsDoNotMatchException exception) {
            //TODO: Change the return
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "login";
    }

    @GetMapping("/login/credentials")
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try {
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);

            return "redirect:/home";
        } catch (InvalidUsernameOrPasswordException | UserNotFoundException e) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/user/login"; //
    }
}
