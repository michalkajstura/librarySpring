package lib.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String tryRegisterUser(RegistrationForm registrationForm,
                                   RedirectAttributes redirectAttributes) {
        User user = registrationForm.toUser(passwordEncoder);

        if (isUserInDatabase(redirectAttributes, user)
            || passwordsDontMatch(registrationForm, redirectAttributes)) {
                return "redirect:/register";
            }

        // Set user role
        Role role = roleRepository.findByRoleName("user");
        user.setRoles(new HashSet<>(Arrays.asList(role)));

        userRepository.save(user);
        return "redirect:/login";
    }

    private boolean isUserInDatabase(RedirectAttributes redirectAttributes, User user) {
        Optional<User> userWithSameName =
            userRepository.findByUsername(user.getUsername());

        if (userWithSameName.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "User already exists");
        }
        return false;
    }

    private boolean passwordsDontMatch(RegistrationForm registrationForm, RedirectAttributes redirectAttributes) {
        if (registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            return false;
        } else {
            redirectAttributes.addFlashAttribute("error", "Passwords don't match");
            return true;
        }
    }
}
