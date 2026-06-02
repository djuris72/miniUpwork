package rs.ac.singidunum.miniUpwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.miniUpwork.model.Skill;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);

    boolean existsByName(String name);

}