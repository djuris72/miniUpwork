package rs.ac.singidunum.miniUpwork.contoller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.ac.singidunum.miniUpwork.model.Review;
import rs.ac.singidunum.miniUpwork.service.ProjectService;
import rs.ac.singidunum.miniUpwork.service.ReviewService;
import rs.ac.singidunum.miniUpwork.service.UserService;

@Controller
@RequestMapping("/web/reviews")
public class ReviewWebController {

    private final ReviewService reviewService;
    private final ProjectService projectService;
    private final UserService userService;

    public ReviewWebController(
            ReviewService reviewService,
            ProjectService projectService,
            UserService userService) {

        this.reviewService = reviewService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "reviews",
                reviewService.findAll());

        return "reviews/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "review",
                new Review());

        model.addAttribute(
                "projects",
                projectService.findAll());

        model.addAttribute(
                "users",
                userService.findAll());

        return "reviews/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Review review) {

        reviewService.save(review);

        return "redirect:/web/reviews";
    }
}
