package rs.ac.singidunum.miniUpwork.contoller;

import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findById(
            @PathVariable Long id) {

        return userService.findById(id);
    }

    @PostMapping
    public User create(
            @RequestBody User user) {

        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(
            @PathVariable Long id,
            @RequestBody User user) {

        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        userService.delete(id);
    }
}