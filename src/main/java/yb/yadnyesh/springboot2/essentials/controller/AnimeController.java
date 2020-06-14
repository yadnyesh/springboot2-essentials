package yb.yadnyesh.springboot2.essentials.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/anime")
@Slf4j
public class AnimeController {

    private DateUtil dateUtil;

    public AnimeController(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @GetMapping("/")
    public List<Anime> listAllAnime() {
        log.info("Formatted Date:  " + dateUtil.formatLocalDateTimeToDatabaseFormat(LocalDateTime.now()));
        return Arrays.asList(
                new Anime("Boku No Hero"),
                new Anime("Berserk"),
                new Anime("Naruto"),
                new Anime("Japanese Animation"));
    }
}
