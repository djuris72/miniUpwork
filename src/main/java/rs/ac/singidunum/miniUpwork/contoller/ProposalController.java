package rs.ac.singidunum.miniUpwork.contoller;

import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.Proposal;
import rs.ac.singidunum.miniUpwork.service.ProposalService;

import java.util.List;

@RestController
@RequestMapping("/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(
            ProposalService proposalService) {

        this.proposalService = proposalService;
    }

    @GetMapping
    public List<Proposal> findAll() {
        return proposalService.findAll();
    }

    @GetMapping("/{id}")
    public Proposal findById(
            @PathVariable Long id) {

        return proposalService.findById(id);
    }

    @PostMapping
    public Proposal create(
            @RequestBody Proposal proposal) {

        return proposalService.save(proposal);
    }

    @PutMapping("/{id}")
    public Proposal update(
            @PathVariable Long id,
            @RequestBody Proposal proposal) {

        return proposalService.update(id, proposal);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        proposalService.delete(id);
    }

    @PutMapping("/{id}/accept")
    public Proposal acceptProposal(
            @PathVariable Long id) {

        return proposalService.acceptProposal(id);
    }

    @GetMapping("/project/{projectId}")
    public List<Proposal> findByProject(
            @PathVariable Long projectId) {

        return proposalService.findByProject(projectId);
    }

    @GetMapping("/freelancer/{freelancerId}")
    public List<Proposal> findByFreelancer(
            @PathVariable Long freelancerId) {

        return proposalService.findByFreelancer(freelancerId);
    }
}