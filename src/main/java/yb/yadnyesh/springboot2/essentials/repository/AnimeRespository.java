package yb.yadnyesh.springboot2.essentials.repository;

import org.springframework.stereotype.Repository;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimeRespository {

    public List<Anime> listAllAnime() {
        List<Anime> animeList = new ArrayList<>();
        animeList.add(new Anime(1,"Boku No Hero"));
        animeList.add(new Anime(2,"Nakula"));
        animeList.add(new Anime(3,"Sahadeva"));
        animeList.add(new Anime(4,"Japanese Animation"));
        return animeList;
    }
}
