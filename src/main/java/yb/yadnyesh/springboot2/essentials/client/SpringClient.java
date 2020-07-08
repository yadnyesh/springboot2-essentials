package yb.yadnyesh.springboot2.essentials.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.wrapper.PageableResponse;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class SpringClient {
    public static void main(String[] args) {
        log.info(new BCryptPasswordEncoder().encode("academy"));
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

//        ResponseEntity<List<Anime>> animeResponseEntity = new RestTemplate()
//                .exchange("http://localhost:8080/animes/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
//                });
//
//        log.info("Anime List: {}", animeResponseEntity.getBody());

        ResponseEntity<PageableResponse<Anime>> animeResponseEntity = new RestTemplate()
                .exchange("http://localhost:8080/animes/", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
                });

        log.info("Anime List: {}", animeResponseEntity.getBody());


        Anime overlord = Anime.builder().name("Overlord").url("http://www.google.com").build();
        Anime overloadSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", overlord, Anime.class);

        log.info("Anime Added id: {}", overloadSaved.getId());

        Anime steinsGate = Anime.builder().name("Steins Gate").url("http://yad.com").build();
        Anime steinsGateSaved = new RestTemplate()
                .exchange("http://localhost:8080/animes/", HttpMethod.POST, new HttpEntity<>(steinsGate,createJsonHeader()), Anime.class).getBody();

        log.info("Anime steinsGateSaved id: {}", steinsGateSaved.getId());
        steinsGateSaved.setName("Steins Gate 2");
        ResponseEntity<Void> updatesSteinsGate = new RestTemplate()
                .exchange("http://localhost:8080/animes/", HttpMethod.PUT, new HttpEntity<>(steinsGateSaved, createJsonHeader()), Void.class);
        log.info("Updated steinsGateSaved id: {}", updatesSteinsGate.getStatusCode());
        ResponseEntity<Void> deletedSteinsGate = new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}", HttpMethod.DELETE, null, Void.class, steinsGateSaved.getId());
        log.info("Deleted steinsGateSaved status", deletedSteinsGate.getStatusCode());
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
