package yb.yadnyesh.springboot2.essentials.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRepository;
import yb.yadnyesh.springboot2.essentials.service.AnimeService;
import yb.yadnyesh.springboot2.essentials.util.AnimeCreator;
import yb.yadnyesh.springboot2.essentials.wrapper.PageableResponse;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimeControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

//    @LocalServerPort
//    private int port;

    @MockBean
    private AnimeRepository animeRepositoryMock;

    @Mock
    private AnimeService animeServiceMock;


    @Lazy
    @TestConfiguration
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("myuser", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("devdojo", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }


    @BeforeEach
    public void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createValidAnime()))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.when(animeRepositoryMock.save(AnimeCreator.createValidUpdatedAnime()))
                .thenReturn(AnimeCreator.createValidUpdatedAnime());
        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("List All returns a pageable list of Animes when successful")
    public void listAllAnime() {
        String expectedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = testRestTemplateRoleUser.exchange("/animes", HttpMethod.GET,
                null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
        }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Returns an Anime By Id when successful")
    public void findById_returnsAnimeWhenSuccessful() {
        int expectedId = AnimeCreator.createValidAnime().getId();
        Anime returnedAnime =  testRestTemplateRoleAdmin.getForObject("/animes/1", Anime.class);
        Assertions.assertThat(returnedAnime).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Saves an Anime By Id when successful")
    public void saveAnime_createsAnimeWhenSuccessful() {
        int expectedId = AnimeCreator.createValidAnime().getId();
        Anime animeToSave = AnimeCreator.createValidAnime();

        Anime returnedAnime = testRestTemplateRoleAdmin.exchange("/animes", HttpMethod.POST, createJsonHttpEntity(animeToSave), Anime.class).getBody();
        Assertions.assertThat(returnedAnime).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Removed Anime By Id when successful")
    public void deleteAnime_createsAnimeWhenSuccessful() {

        ResponseEntity<Void> responseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/1", HttpMethod.DELETE, null, Void.class);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Delete Anime is only allowed for Admin users")
    public void deleteAnime_returns403WhenUserIsNotAdmin() {

        ResponseEntity<Void> responseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/1", HttpMethod.DELETE, null, Void.class);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Updates an Anime By Id when successful")
    public void updateAnime_createsAnimeWhenSuccessful() {

        Anime validAnime = AnimeCreator.createValidAnime();
        String expectedName = validAnime.getName();

        ResponseEntity<Void> responseEntity = testRestTemplateRoleAdmin.exchange("/animes", HttpMethod.PUT, createJsonHttpEntity(validAnime), Void.class);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    private HttpEntity<Anime> createJsonHttpEntity(Anime anime) {
        return new HttpEntity<>(anime, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
