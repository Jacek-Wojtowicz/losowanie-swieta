package com.jwojtowicz.losowanie.home;

import com.jwojtowicz.losowanie.pair.PairService;
import com.jwojtowicz.losowanie.user.User;
import com.jwojtowicz.losowanie.user.UserDTO;
import com.jwojtowicz.losowanie.user.UserService;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthController {

    private final PairService pairService;
    private UserService service;

    @GetMapping("/index")
    public String home(Model model) {
        String user = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        Optional<String> receiverForGiver = pairService.findReceiverForGiver(user);
        return "index";
    }

    // handler method to handle login request
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/losowanie")
    public String losowanie(Model model) {
        String user = SecurityContextHolder.getContext().getAuthentication()
                .getName();
        Optional<String> receiverForGiver = pairService.findReceiverForGiver(user);
        String message = receiverForGiver.map(s -> "W tym roku robisz prezent dla:" + s).orElse("Losowania jeszcze nie bylo");
        model.addAttribute("message", message);
        return "/losowanie";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDTO dto,
            BindingResult result, Model model) {

        Optional<User> existingUser = service.getUserByName(dto.getName());

        if (existingUser.isPresent()) {
            result.rejectValue("name", null,
                    "Użytkownik o takim imieniu już istnieje!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", dto);
            return "/register";
        }

        service.createUser(dto);
        model.addAttribute("userName", dto.getName());
        return "redirect:/login";
    }
}
