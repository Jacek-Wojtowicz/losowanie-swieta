package com.jwojtowicz.losowanie.admin;

import com.jwojtowicz.losowanie.pair.PairDTO;
import com.jwojtowicz.losowanie.pair.PairService;
import com.jwojtowicz.losowanie.user.UserDTO;
import com.jwojtowicz.losowanie.user.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private UserService userService;
    private PairService pairService;

    @GetMapping
    public String loadData(Model model) {
        List<UserDTO> users = userService.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
        List<PairDTO> pairs = pairService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("pairs", pairs);
        return "admin";
    }

    @PutMapping("/user/drawname/{id}")
    public String updateUser(@PathVariable("id") Integer id, Model model, @RequestParam String drawName) {
        userService.updateDrawName(id, drawName);
        return "redirect:/admin";
    }

    @PutMapping("/user/password/{id}")
    public String updateUserPassword(@PathVariable("id") Integer id, Model model, @RequestParam String password) {
        userService.updatePassword(id, password);
        return "redirect:/admin";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/draw")
    public String draw(Model model) {
        pairService.draw();
        return "redirect:/admin";
    }

}
