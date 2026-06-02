package rs.ac.singidunum.miniUpwork.contoller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.ac.singidunum.miniUpwork.model.Proposal;
import rs.ac.singidunum.miniUpwork.service.ProjectService;
import rs.ac.singidunum.miniUpwork.service.ProposalService;
import rs.ac.singidunum.miniUpwork.service.UserService;

@Controller
@RequestMapping("/web/proposals")
public class ProposalWebController {

    private final ProposalService proposalService;
    private final ProjectService projectService;
    private final UserService userService;

    public ProposalWebController(
            ProposalService proposalService,
            ProjectService projectService,
            UserService userService) {

        this.proposalService = proposalService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "proposals",
                proposalService.findAll());

        return "proposals/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "proposal",
                new Proposal());

        model.addAttribute(
                "projects",
                projectService.findAll());

        model.addAttribute(
                "users",
                userService.findAll());

        return "proposals/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Proposal proposal) {

        proposalService.save(proposal);

        return "redirect:/web/proposals";
    }

    @PostMapping("/accept/{id}")
    public String accept(
            @PathVariable Long id) {

        proposalService.acceptProposal(id);

        return "redirect:/web/proposals";
    }
}
