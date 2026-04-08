package by.yurnerix.repository;

import by.yurnerix.entity.Role;
import by.yurnerix.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);

}
