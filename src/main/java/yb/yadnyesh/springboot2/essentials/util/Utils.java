package yb.yadnyesh.springboot2.essentials.util;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.exception.ResourceNotFoundException;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class Utils {

    AnimeRepository animeRepository;

    public String formatLocalDateTimeToDatabaseFormat(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    public Anime findAnimeOrThrowNotFound(int id){
        return animeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Anime not found"));

    }
}
