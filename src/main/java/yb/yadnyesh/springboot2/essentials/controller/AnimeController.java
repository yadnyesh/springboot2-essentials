package yb.yadnyesh.springboot2.essentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRespository;
import yb.yadnyesh.springboot2.essentials.util.DateUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeRespository animeRespository;

    @GetMapping
    public ResponseEntity<List<Anime>> listAllAnime() {
        log.info("Formatted Date:  " + dateUtil.formatLocalDateTimeToDatabaseFormat(LocalDateTime.now()));
        return ResponseEntity.ok(animeRespository.listAllAnime());
    }

    @GetMapping("/{animeId}")
    public ResponseEntity<Anime> findAnimeById(@PathVariable int animeId) {
        Anime animeFound = animeRespository.listAllAnime()
                .stream()
                .filter(s -> s.getId() == animeId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        return ResponseEntity.ok(animeFound);
    }

    @PostMapping
    public ResponseEntity<Anime> saveAnime(@RequestBody Anime anime) {
        return ResponseEntity.ok(animeRespository.save(anime));
    }
}
