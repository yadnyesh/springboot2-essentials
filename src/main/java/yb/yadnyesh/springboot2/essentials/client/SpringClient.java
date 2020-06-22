package yb.yadnyesh.springboot2.essentials.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import java.util.Arrays;
import java.util.List;

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

//        Anime[] animeArray = new RestTemplate()
//                .getForObject("http://localhost:8080/animes/", Anime[].class);
//        log.info("Anime Array: {}", Arrays.toString(animeArray));

        ResponseEntity<List<Anime>> animeResponseEntity = new RestTemplate()
                .exchange("http://localhost:8080/animes/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
                });

        log.info("Anime List: {}", animeResponseEntity.getBody());

    }
}
