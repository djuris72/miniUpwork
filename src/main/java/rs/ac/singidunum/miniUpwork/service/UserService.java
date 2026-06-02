package rs.ac.singidunum.miniUpwork.service;

import java.util.List;

import org.springframework.stereotype.Service;

import rs.ac.singidunum.miniUpwork.exception.ResourceNotFoundException;
import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    public User save(User user) {

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException(
                    "Email already exists");
        }

        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {

        User user = findById(id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.delete(findById(id));
    }
}
