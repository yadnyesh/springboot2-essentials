package yb.yadnyesh.springboot2.essentials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
    List<Anime> findByName(String name);

}
