package rs.ac.singidunum.miniUpwork.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import rs.ac.singidunum.miniUpwork.enums.ProjectStatus;
import rs.ac.singidunum.miniUpwork.enums.Role;
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
                proposalService.findAllWithDetails());

        return "proposals/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "proposal",
                new Proposal());

        model.addAttribute(
                "projects",
                projectService.findByStatus(ProjectStatus.OPEN));

        model.addAttribute(
                "freelancers",
                userService.findByRoleWithSkills(Role.FREELANCER));

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

    @PostMapping("/reject/{id}")
    public String reject(
            @PathVariable Long id) {

        proposalService.rejectProposal(id);

        return "redirect:/web/proposals";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "proposal",
                proposalService.findById(id));

        return "proposals/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Proposal proposal) {

        proposalService.update(id, proposal);

        return "redirect:/web/proposals";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        proposalService.delete(id);

        return "redirect:/web/proposals";
    }
}
