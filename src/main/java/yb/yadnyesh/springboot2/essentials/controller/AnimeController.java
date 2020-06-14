package yb.yadnyesh.springboot2.essentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRespository;
import yb.yadnyesh.springboot2.essentials.util.Utils;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {

    private final Utils utils;
    private final AnimeRespository animeRespository;

    @GetMapping
    public ResponseEntity<List<Anime>> listAllAnime() {
        log.info("Formatted Date:  " + utils.formatLocalDateTimeToDatabaseFormat(LocalDateTime.now()));
        return ResponseEntity.ok(animeRespository.listAllAnime());
    }

    @GetMapping("/{animeId}")
    public ResponseEntity<Anime> findAnimeById(@PathVariable int animeId) {
        return ResponseEntity.ok(animeRespository.findById(animeId));
    }

    @PostMapping
    public ResponseEntity<Anime> saveAnime(@RequestBody Anime anime) {
        return ResponseEntity.ok(animeRespository.save(anime));
    }

    @DeleteMapping("/{animeId}")
    public ResponseEntity<Anime> deleteAnime(@PathVariable int animeId) {
        animeRespository.delete(animeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> updateAnime(@RequestBody Anime anime) {
        animeRespository.update(anime);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
