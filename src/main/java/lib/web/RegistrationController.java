package lib.web;

import lib.account.UserRepository;
import lib.account.User;
import lib.account.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
//        return (principal == null) ? "registration" : "redirect:/";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm, RedirectAttributes redirectAttributes) {
        User user = registrationForm.toUser(passwordEncoder);

        Optional<User> userWithSameName =
                userRepository.findByUsername(user.getUsername());

        if (userWithSameName.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "User already exists");
            return "redirect:/register";
        }

        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Passwords don't match");
            return "redirect:/register";
        }
        userRepository.save(user);
        return "redirect:/login";
    }

}
