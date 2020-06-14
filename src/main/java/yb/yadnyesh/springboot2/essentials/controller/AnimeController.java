package yb.yadnyesh.springboot2.essentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.service.AnimeService;
import yb.yadnyesh.springboot2.essentials.util.Utils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/animes")
@Slf4j
@RequiredArgsConstructor
public class AnimeController {

    private final Utils utils;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> listAllAnime() {
        return ResponseEntity.ok(animeService.listAllAnime());
    }

    @GetMapping("/{animeId}")
    public ResponseEntity<Anime> findAnimeById(@PathVariable int animeId) {
        return ResponseEntity.ok(animeService.findById(animeId));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findAnimeById(@RequestParam(value = "name") String name) {
        log.info(name);
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> saveAnime(@RequestBody @Valid Anime anime) {
        return ResponseEntity.ok(animeService.save(anime));
    }

    @DeleteMapping("/{animeId}")
    public ResponseEntity<Anime> deleteAnime(@PathVariable int animeId) {
        animeService.delete(animeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Anime> updateAnime(@RequestBody Anime anime) {
        animeService.update(anime);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
