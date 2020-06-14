package yb.yadnyesh.springboot2.essentials.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    @GetMapping("/")
    public List<Anime> listAllAnime() {
        return Arrays.asList(new Anime("DBZ"), new Anime("Berserk"));
    }
}
