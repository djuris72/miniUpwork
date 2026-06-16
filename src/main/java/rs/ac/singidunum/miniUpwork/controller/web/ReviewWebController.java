package rs.ac.singidunum.miniUpwork.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.ac.singidunum.miniUpwork.model.Review;
import rs.ac.singidunum.miniUpwork.service.ReviewService;

@Controller
@RequestMapping("/web/reviews")
public class ReviewWebController {

    private final ReviewService reviewService;

    public ReviewWebController(ReviewService reviewService) {
        this.reviewService = reviewService;
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
                reviewService.findReviewableProjects());

        return "reviews/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Review review) {

        reviewService.save(review);

        return "redirect:/web/reviews";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "review",
                reviewService.findById(id));

        return "reviews/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Review review) {

        reviewService.update(id, review);

        return "redirect:/web/reviews";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        reviewService.delete(id);

        return "redirect:/web/reviews";
    }
}
