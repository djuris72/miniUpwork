package rs.ac.singidunum.miniUpwork.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.model.Proposal;
import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.enums.ProjectStatus;
import rs.ac.singidunum.miniUpwork.enums.ProposalStatus;
import rs.ac.singidunum.miniUpwork.enums.Role;
import rs.ac.singidunum.miniUpwork.exception.BusinessException;
import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.repository.ProjectRepository;
import rs.ac.singidunum.miniUpwork.repository.ProposalRepository;
import rs.ac.singidunum.miniUpwork.repository.UserRepository;

import java.util.List;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProposalService(
            ProposalRepository proposalRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository) {

        this.proposalRepository = proposalRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<Proposal> findAll() {
        return proposalRepository.findAll();
    }

    public Proposal findById(Long id) {
        return proposalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Proposal not found"));
    }

    public Proposal save(Proposal proposal) {

        Long projectId = proposal.getProject().getId();
        Long freelancerId = proposal.getFreelancer().getId();

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found"));

        User freelancer =
                userRepository.findById(freelancerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Freelancer not found"));

        if (freelancer.getRole() != Role.FREELANCER) {
            throw new BusinessException(
                    "Only freelancers can apply");
        }

        if (project.getClient()
                .getId()
                .equals(freelancer.getId())) {

            throw new BusinessException(
                    "Cannot apply to your own project");
        }

        if (project.getStatus()
                != ProjectStatus.OPEN) {

            throw new BusinessException(
                    "Project is not open");
        }

        if (proposalRepository
                .existsByProjectIdAndFreelancerId(
                        projectId,
                        freelancerId)) {

            throw new BusinessException(
                    "You already applied");
        }

        proposal.setProject(project);
        proposal.setFreelancer(freelancer);

        return proposalRepository.save(proposal);
    }

    public Proposal update(Long id, Proposal updatedProposal) {

        Proposal proposal = findById(id);

        if (proposal.getStatus()
                != ProposalStatus.PENDING) {

            throw new BusinessException(
                    "Only pending proposal can be edited");
        }

        proposal.setPrice(updatedProposal.getPrice());
        proposal.setMessage(updatedProposal.getMessage());

        return proposalRepository.save(proposal);
    }

    public void delete(Long id) {

        Proposal proposal = findById(id);

        if (proposal.getStatus()
                == ProposalStatus.ACCEPTED) {

            throw new BusinessException(
                    "Accepted proposal cannot be deleted");
        }

        proposalRepository.delete(proposal);
    }

    @Transactional
    public Proposal acceptProposal(Long proposalId) {

        Proposal acceptedProposal =
                findById(proposalId);

        Project project =
                acceptedProposal.getProject();

        List<Proposal> proposals =
                proposalRepository.findByProjectId(
                        project.getId());

        for (Proposal proposal : proposals) {

            if (proposal.getId()
                    .equals(proposalId)) {

                proposal.setStatus(
                        ProposalStatus.ACCEPTED);

            } else {

                proposal.setStatus(
                        ProposalStatus.REJECTED);
            }

            proposalRepository.save(proposal);
        }

        project.setAssignedFreelancer(
                acceptedProposal.getFreelancer());

        project.setStatus(
                ProjectStatus.IN_PROGRESS);

        projectRepository.save(project);

        return acceptedProposal;
    }

    public List<Proposal> findByProject(Long projectId) {
        return proposalRepository.findByProjectId(projectId);
    }

    public List<Proposal> findByFreelancer(Long freelancerId) {
        return proposalRepository.findByFreelancerId(freelancerId);
    }
}