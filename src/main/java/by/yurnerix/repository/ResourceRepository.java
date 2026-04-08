package by.yurnerix.repository;

import by.yurnerix.entity.Resource;
import by.yurnerix.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByActiveTrue();
    List<Resource> findByType(ResourceType type);
    List<Resource> findByLocationContainingIgnoreCase(String location);

}