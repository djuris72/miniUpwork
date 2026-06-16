package rs.ac.singidunum.miniUpwork.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.enums.Role;
import rs.ac.singidunum.miniUpwork.exception.BusinessException;
import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.model.Skill;
import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.repository.SkillRepository;
import rs.ac.singidunum.miniUpwork.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    public UserService(
            UserRepository userRepository,
            SkillRepository skillRepository) {

        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllWithSkills() {
        return userRepository.findAllWithSkills();
    }

    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> findByRoleWithSkills(Role role) {
        return userRepository.findByRoleWithSkills(role);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    public User findByIdWithSkills(Long id) {
        return userRepository.findByIdWithSkills(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    public User save(User user) {

        if (user.getRole() == null) {
            throw new BusinessException("Role is required");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException(
                    "Email already exists");
        }

        applySkills(user, user.getSkills());

        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {

        User user = findByIdWithSkills(id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        applySkills(user, updatedUser.getSkills());

        return userRepository.save(user);
    }

    private void applySkills(User user, Set<Skill> selectedSkills) {

        if (user.getRole() != Role.FREELANCER) {
            user.getSkills().clear();
            return;
        }

        Set<Long> skillIds = selectedSkills == null
                ? Set.of()
                : selectedSkills.stream()
                        .map(Skill::getId)
                        .filter(id -> id != null)
                        .collect(Collectors.toSet());

        Set<Skill> resolved = new HashSet<>();
        for (Long skillId : skillIds) {
            resolved.add(skillRepository.findById(skillId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Skill not found")));
        }

        user.getSkills().clear();
        user.getSkills().addAll(resolved);
    }

    public void delete(Long id) {
        userRepository.delete(findById(id));
    }
}
