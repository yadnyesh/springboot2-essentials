package yb.yadnyesh.springboot2.essentials.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRepository;
import yb.yadnyesh.springboot2.essentials.util.Utils;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnimeService {

    private final Utils utils;
    private final AnimeRepository animeRepository;

    public Page<Anime> listAllAnime(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime save(Anime animeToAdd) {
        return animeRepository.save(animeToAdd);
    }

    public Anime findById(int animeId) {
        return utils.findAnimeOrThrowNotFound(animeId);
    }

    public void delete(int animeId) {
        Anime animeToBeDeleted = utils.findAnimeOrThrowNotFound(animeId);
        animeRepository.deleteById(animeToBeDeleted.getId());
    }

    public void update(Anime anime) {
        animeRepository.save(anime);
    }
}
