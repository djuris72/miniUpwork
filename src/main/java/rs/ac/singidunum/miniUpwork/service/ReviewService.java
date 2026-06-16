package rs.ac.singidunum.miniUpwork.service;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.enums.ProjectStatus;
import rs.ac.singidunum.miniUpwork.exception.BusinessException;
import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.model.Review;
import rs.ac.singidunum.miniUpwork.repository.ProjectRepository;
import rs.ac.singidunum.miniUpwork.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            ProjectRepository projectRepository) {

        this.reviewRepository = reviewRepository;
        this.projectRepository = projectRepository;
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found"));
    }

    public Review save(Review review) {

        Long projectId = review.getProject().getId();

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found"));

        if (project.getStatus() != ProjectStatus.COMPLETED) {
            throw new BusinessException(
                    "Review can only be left for completed projects");
        }

        if (project.getAssignedFreelancer() == null) {
            throw new BusinessException(
                    "Project has no assigned freelancer");
        }

        if (reviewRepository.existsByProjectId(projectId)) {
            throw new BusinessException(
                    "Review already exists for this project");
        }

        if (review.getRating() < 1
                || review.getRating() > 5) {
            throw new BusinessException(
                    "Rating must be between 1 and 5");
        }

        review.setProject(project);
        review.setReviewer(project.getClient());
        review.setReviewedUser(project.getAssignedFreelancer());

        return reviewRepository.save(review);
    }

    public Review update(Long id, Review updatedReview) {

        Review review = findById(id);

        if (updatedReview.getRating() < 1
                || updatedReview.getRating() > 5) {

            throw new BusinessException(
                    "Rating must be between 1 and 5");
        }

        review.setRating(updatedReview.getRating());
        review.setComment(updatedReview.getComment());

        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.delete(findById(id));
    }

    public List<Review> findByReviewedUser(Long userId) {
        return reviewRepository.findByReviewedUserId(userId);
    }

    public Double getAverageRating(Long userId) {

        Double average =
                reviewRepository.findAverageRatingForUser(
                        userId);

        return average == null ? 0.0 : average;
    }

    public List<Project> findReviewableProjects() {
        return projectRepository.findByStatus(ProjectStatus.COMPLETED)
                .stream()
                .filter(p -> p.getAssignedFreelancer() != null)
                .filter(p -> !reviewRepository.existsByProjectId(p.getId()))
                .toList();
    }
}