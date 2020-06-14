package yb.yadnyesh.springboot2.essentials.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class Utils {
    public String formatLocalDateTimeToDatabaseFormat(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }

    public Anime findAnimeOrThrowNotFound(int id, List<Anime> animeList){
        return animeList.stream()
            .filter(s -> s.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Animee not found"));
    }
}
