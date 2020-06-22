package yb.yadnyesh.springboot2.essentials.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.util.Arrays;

@Slf4j
public class SpringClient {
    public static void main(String[] args) {
//        ResponseEntity<Anime> animeResponseEntity = new RestTemplate()
//                                                    .getForEntity("http://localhost:8080/animes/2", Anime.class);
//        log.info("Response Entity {}", animeResponseEntity);
//        log.info("Response Data {}", animeResponseEntity.getBody());

//        Anime anime = new RestTemplate()
//                .getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
//        log.info("Anime name: {}", anime.toString());

        Anime[] animeArray = new RestTemplate()
                .getForObject("http://localhost:8080/animes/", Anime[].class);
        log.info("Anime Array: {}", Arrays.toString(animeArray));
    }
}
