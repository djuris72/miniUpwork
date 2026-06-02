package rs.ac.singidunum.miniUpwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.singidunum.miniUpwork.model.User;
import rs.ac.singidunum.miniUpwork.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);

}