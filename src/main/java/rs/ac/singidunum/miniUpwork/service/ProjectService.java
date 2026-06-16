package rs.ac.singidunum.miniUpwork.service;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.model.Category;
import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.enums.ProjectStatus;
import rs.ac.singidunum.miniUpwork.enums.Role;
import rs.ac.singidunum.miniUpwork.exception.BusinessException;
import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.repository.CategoryRepository;
import rs.ac.singidunum.miniUpwork.repository.ProjectRepository;
import rs.ac.singidunum.miniUpwork.repository.UserRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));
    }

    public Project save(Project project) {

        if (project.getClient() == null
                || project.getClient().getId() == null) {
            throw new BusinessException(
                    "Client is required");
        }

        User client = userRepository.findById(
                        project.getClient().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Client not found"));

        if (client.getRole() != Role.CLIENT) {
            throw new BusinessException(
                    "Only clients can create projects");
        }

        if (project.getCategory() == null
                || project.getCategory().getId() == null) {
            throw new BusinessException(
                    "Category is required");
        }

        Long categoryId = project.getCategory().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        project.setClient(client);
        project.setCategory(category);

        return projectRepository.save(project);
    }

    public Project update(Long id, Project updatedProject) {

        Project project = findById(id);

        if (project.getStatus() != ProjectStatus.OPEN) {
            throw new BusinessException(
                    "Only open projects can be edited");
        }

        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        project.setBudget(updatedProject.getBudget());

        if (updatedProject.getCategory() != null) {

            Long categoryId =
                    updatedProject.getCategory().getId();

            Category category =
                    categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Category not found"));

            project.setCategory(category);
        }

        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.delete(findById(id));
    }

    public Project completeProject(Long id) {

        Project project = findById(id);

        if (project.getStatus()
                != ProjectStatus.IN_PROGRESS) {

            throw new BusinessException(
                    "Project must be in progress");
        }

        project.setStatus(ProjectStatus.COMPLETED);

        return projectRepository.save(project);
    }

    public Project cancelProject(Long id) {

        Project project = findById(id);

        if (project.getStatus()
                == ProjectStatus.COMPLETED) {

            throw new BusinessException(
                    "Completed project cannot be cancelled");
        }

        project.setStatus(ProjectStatus.CANCELLED);

        return projectRepository.save(project);
    }

    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> findByCategory(Long categoryId) {
        return projectRepository.findByCategoryId(categoryId);
    }

    public List<Project> findByClient(Long clientId) {
        return projectRepository.findByClientId(clientId);
    }

    public List<Project> search(String keyword) {

        return projectRepository
                .findByTitleContainingIgnoreCase(
                        keyword
                );
    }
}