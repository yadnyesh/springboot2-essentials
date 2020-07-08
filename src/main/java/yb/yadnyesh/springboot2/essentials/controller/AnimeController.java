package yb.yadnyesh.springboot2.essentials.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/list")
    public List<Anime> getAllAnimeAsList(Pageable pageable) {
        return animeService.getAllAnimeAsList();
    }

    @GetMapping
    public ResponseEntity<Page<Anime>> listAllAnime(Pageable pageable) {
        return ResponseEntity.ok(animeService.listAllAnime(pageable));
    }

    @GetMapping("/{animeId}")
    public ResponseEntity<Anime> findAnimeById(@PathVariable int animeId) {
        //log.info(userDetails.toString());
        return ResponseEntity.ok(animeService.findById(animeId));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Anime>> findAnimeById(@RequestParam(value = "name") String name) {
        log.info(name);
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Anime> saveAnime(@RequestBody @Valid Anime anime) {
        return ResponseEntity.ok(animeService.save(anime));
    }

    @DeleteMapping("/{animeId}")
    public ResponseEntity<Void> deleteAnime(@PathVariable int animeId) {
        animeService.delete(animeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> updateAnime(@RequestBody Anime anime) {
        animeService.update(anime);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
