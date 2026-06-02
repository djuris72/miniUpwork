package rs.ac.singidunum.miniUpwork.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.exception.BusinessException;
import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.model.Skill;
import rs.ac.singidunum.miniUpwork.repository.SkillRepository;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(
            SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Skill findById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Skill not found"));
    }

    public Skill save(Skill skill) {

        if(skillRepository.existsByName(skill.getName())) {
            throw new BusinessException(
                    "Skill already exists");
        }

        return skillRepository.save(skill);
    }
}
