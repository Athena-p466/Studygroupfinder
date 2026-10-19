package p466.studygroupfinder.security;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import p466.studygroupfinder.data.UserRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegisterForm form, Errors errors){
        if (errors.hasErrors()){
            return "registration";
        }
        userRepo.save(form.toUser(passwordEncoder));
        return"redirect:/login";
    }


}
