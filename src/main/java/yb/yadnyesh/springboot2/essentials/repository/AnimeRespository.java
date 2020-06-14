package yb.yadnyesh.springboot2.essentials.repository;

import org.springframework.stereotype.Repository;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class AnimeRespository {

    private static List<Anime> animeList;
    static {
        animeList = new ArrayList<>(List.of(
                new Anime(1,"Boku No Hero"),
                new Anime(2,"Nakula"),
                new Anime(3,"Sahadeva"),
                new Anime(4,"Japanese Animation")
        ));
    }

    public List<Anime> listAllAnime() {
        return animeList;
    }

    public Anime save(Anime animeToAdd) {
        animeToAdd.setId(ThreadLocalRandom.current().nextInt(4,100000));
        animeList.add(animeToAdd);
        return animeToAdd;
    }
}
