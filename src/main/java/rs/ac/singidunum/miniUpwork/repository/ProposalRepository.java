package rs.ac.singidunum.miniUpwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

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

}