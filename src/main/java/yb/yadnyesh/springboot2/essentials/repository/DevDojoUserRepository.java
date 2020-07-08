package yb.yadnyesh.springboot2.essentials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yb.yadnyesh.springboot2.essentials.domain.DevDojoUser;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Integer> {
    DevDojoUser findByUsername(String name);
}
