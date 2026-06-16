package rs.ac.singidunum.miniUpwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.singidunum.miniUpwork.model.Proposal;
import rs.ac.singidunum.miniUpwork.enums.ProposalStatus;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    List<Proposal> findByProjectId(Long projectId);

    List<Proposal> findByFreelancerId(Long freelancerId);

    boolean existsByProjectIdAndFreelancerId(
            Long projectId,
            Long freelancerId
    );

    List<Proposal> findByStatus(ProposalStatus status);
    
    long countByProjectId(Long projectId);

    @Query("""
            SELECT DISTINCT p FROM Proposal p
            JOIN FETCH p.project
            JOIN FETCH p.freelancer f
            LEFT JOIN FETCH f.skills
            """)
    List<Proposal> findAllWithDetails();

}