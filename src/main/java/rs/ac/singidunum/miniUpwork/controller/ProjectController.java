package rs.ac.singidunum.miniUpwork.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.enums.ProjectStatus;
import rs.ac.singidunum.miniUpwork.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(
            ProjectService projectService) {

        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public Project findById(
            @PathVariable Long id) {

        return projectService.findById(id);
    }

    @PostMapping
    public Project create(
            @RequestBody Project project) {

        return projectService.save(project);
    }

    @PutMapping("/{id}")
    public Project update(
            @PathVariable Long id,
            @RequestBody Project project) {

        return projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        projectService.delete(id);
    }

    @PutMapping("/{id}/complete")
    public Project completeProject(
            @PathVariable Long id) {

        return projectService.completeProject(id);
    }

    @PutMapping("/{id}/cancel")
    public Project cancelProject(
            @PathVariable Long id) {

        return projectService.cancelProject(id);
    }

    @GetMapping("/status/{status}")
    public List<Project> findByStatus(
            @PathVariable ProjectStatus status) {

        return projectService.findByStatus(status);
    }

    @GetMapping("/category/{categoryId}")
    public List<Project> findByCategory(
            @PathVariable Long categoryId) {

        return projectService.findByCategory(categoryId);
    }

    @GetMapping("/client/{clientId}")
    public List<Project> findByClient(
            @PathVariable Long clientId) {

        return projectService.findByClient(clientId);
    }

    @GetMapping("/search")
    public List<Project> search(
            @RequestParam String keyword) {

        return projectService.search(keyword);
    }
}