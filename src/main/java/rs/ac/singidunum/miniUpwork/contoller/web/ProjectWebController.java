package rs.ac.singidunum.miniUpwork.contoller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.service.CategoryService;
import rs.ac.singidunum.miniUpwork.service.ProjectService;

@Controller
@RequestMapping("/web/projects")
public class ProjectWebController {

    private final ProjectService projectService;
    private final CategoryService categoryService;

    public ProjectWebController(
            ProjectService projectService,
            CategoryService categoryService) {

        this.projectService = projectService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "projects",
                projectService.findAll());

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
}