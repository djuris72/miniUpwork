package rs.ac.singidunum.miniUpwork.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.enums.Role;
import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.service.CategoryService;
import rs.ac.singidunum.miniUpwork.service.ProjectService;
import rs.ac.singidunum.miniUpwork.service.ProposalService;
import rs.ac.singidunum.miniUpwork.service.UserService;

@Controller
@RequestMapping("/web/projects")
public class ProjectWebController {

    private final ProjectService projectService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProposalService proposalService;

    public ProjectWebController(
            ProjectService projectService,
            CategoryService categoryService,
            UserService userService,
            ProposalService proposalService) {

        this.projectService = projectService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.proposalService = proposalService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String search,
            Model model) {

        if (search != null && !search.isBlank()) {
            model.addAttribute(
                    "projects",
                    projectService.search(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute(
                    "projects",
                    projectService.findAll());
        }

        return "projects/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "project",
                new Project());

        model.addAttribute(
                "categories",
                categoryService.findAll());

        model.addAttribute(
                "clients",
                userService.findByRole(Role.CLIENT));

        return "projects/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Project project) {

        projectService.save(project);

        return "redirect:/web/projects";
    }
    
    @GetMapping("/{id}")
    public String details(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "project",
                projectService.findById(id));

        model.addAttribute(
                "proposals",
                proposalService.findByProject(id));

        return "projects/details";
    }
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "project",
                projectService.findById(id));

        model.addAttribute(
                "categories",
                categoryService.findAll());

        return "projects/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute Project project) {

        projectService.update(id, project);

        return "redirect:/web/projects";
    }
    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        projectService.delete(id);

        return "redirect:/web/projects";
    }

    @PostMapping("/{id}/complete")
    public String complete(
            @PathVariable Long id) {

        projectService.completeProject(id);

        return "redirect:/web/projects/" + id;
    }

    @PostMapping("/{id}/cancel")
    public String cancel(
            @PathVariable Long id) {

        projectService.cancelProject(id);

        return "redirect:/web/projects/" + id;
    }
}