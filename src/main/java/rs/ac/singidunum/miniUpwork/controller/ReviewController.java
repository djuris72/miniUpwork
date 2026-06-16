package rs.ac.singidunum.miniUpwork.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.Review;
import rs.ac.singidunum.miniUpwork.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(
            ReviewService reviewService) {

        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public Review findById(
            @PathVariable Long id) {

        return reviewService.findById(id);
    }

    @PostMapping
    public Review create(
            @RequestBody Review review) {

        return reviewService.save(review);
    }

    @PutMapping("/{id}")
    public Review update(
            @PathVariable Long id,
            @RequestBody Review review) {

        return reviewService.update(id, review);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        reviewService.delete(id);
    }

    @GetMapping("/user/{userId}")
    public List<Review> findByReviewedUser(
            @PathVariable Long userId) {

        return reviewService.findByReviewedUser(userId);
    }

    @GetMapping("/average-rating/{userId}")
    public Double averageRating(
            @PathVariable Long userId) {

        return reviewService.getAverageRating(userId);
    }
}