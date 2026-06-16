package rs.ac.singidunum.miniUpwork.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.ac.singidunum.miniUpwork.enums.Role;
import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.service.SkillService;
import rs.ac.singidunum.miniUpwork.service.UserService;

@Controller
@RequestMapping("/web/users")
public class UserWebController {

    private final UserService userService;
    private final SkillService skillService;

    public UserWebController(
            UserService userService,
            SkillService skillService) {

        this.userService = userService;
        this.skillService = skillService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAllWithSkills());
        return "users/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("allSkills", skillService.findAll());
        return "users/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/web/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("user", userService.findByIdWithSkills(id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("allSkills", skillService.findAll());
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute User user) {

        userService.update(id, user);
        return "redirect:/web/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/web/users";
    }
}
