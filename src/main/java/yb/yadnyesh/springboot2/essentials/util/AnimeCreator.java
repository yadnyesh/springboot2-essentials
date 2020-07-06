package yb.yadnyesh.springboot2.essentials.util;

import yb.yadnyesh.springboot2.essentials.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Tensai Shitara Slime Datta Ken")
                .url("http://www.google.com")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .id(2758)
                .name("Tensai Shitara Slime Datta Ken")
                .url("http://www.google.com")
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Tensai Shitara Slime Datta Ken Updated")
                .url("http://www.google.com")
                .build();
    }
}
