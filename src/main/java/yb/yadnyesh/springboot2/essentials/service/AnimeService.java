package yb.yadnyesh.springboot2.essentials.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRepository;
import yb.yadnyesh.springboot2.essentials.util.Utils;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnimeService {

    private final Utils utils;
    private final AnimeRepository animeRepository;

//    private static List<Anime> animeList;
//
//    static {
//        animeList = new ArrayList<>(List.of(
//                new Anime(1,"Boku No Hero"),
//                new Anime(2,"Nakula"),
//                new Anime(3,"Sahadeva"),
//                new Anime(4,"Japanese Animation")
//        ));
//    }

    public List<Anime> listAllAnime() {
        return animeRepository.findAll();
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
