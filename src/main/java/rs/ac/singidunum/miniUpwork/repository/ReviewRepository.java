package rs.ac.singidunum.miniUpwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.singidunum.miniUpwork.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByReviewedUserId(Long userId);

    boolean existsByProjectId(Long projectId);
    
    @Query("""
    	    SELECT AVG(r.rating)
    	    FROM Review r
    	    WHERE r.reviewedUser.id = :userId
    	""")
    	Double findAverageRatingForUser(Long userId);

}