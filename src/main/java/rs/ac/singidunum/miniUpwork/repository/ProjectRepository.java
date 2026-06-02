package rs.ac.singidunum.miniUpwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.singidunum.miniUpwork.model.Project;
import rs.ac.singidunum.miniUpwork.enums.ProjectStatus;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByClientId(Long clientId);

    List<Project> findByCategoryId(Long categoryId);
    
    long countByClientIdAndStatus(
            Long clientId,
            ProjectStatus status
    );
    
    List<Project>
    findByTitleContainingIgnoreCase(String title);

}