package rs.ac.singidunum.miniUpwork.contoller;

import org.springframework.web.bind.annotation.*;

import rs.ac.singidunum.miniUpwork.model.Skill;
import rs.ac.singidunum.miniUpwork.service.SkillService;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(
            SkillService skillService) {

        this.skillService = skillService;
    }

    @GetMapping
    public List<Skill> findAll() {
        return skillService.findAll();
    }

    @GetMapping("/{id}")
    public Skill findById(
            @PathVariable Long id) {

        return skillService.findById(id);
    }

    @PostMapping
    public Skill create(
            @RequestBody Skill skill) {

        return skillService.save(skill);
    }
}