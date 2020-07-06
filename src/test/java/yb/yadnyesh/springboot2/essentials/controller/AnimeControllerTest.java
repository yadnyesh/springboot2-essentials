package yb.yadnyesh.springboot2.essentials.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.service.AnimeService;
import yb.yadnyesh.springboot2.essentials.util.AnimeCreator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    public void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAllAnime(ArgumentMatchers.any()))
                .thenReturn(animePage);
        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.when(animeServiceMock.save(AnimeCreator.createValidAnime()))
                .thenReturn(AnimeCreator.createValidAnime());
        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("List All returns a pageable list of Animes when successful")
    public void listAllAnime() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.listAllAnime(null).getBody();
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Returns an Anime By Id when successful")
    public void findById_returnsAnimeWhenSuccessful() {
        int expectedId = AnimeCreator.createValidAnime().getId();
        Anime returnedAnime = animeController.findAnimeById(1).getBody();
        Assertions.assertThat(returnedAnime).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Saves an Anime By Id when successful")
    public void saveAnime_createsAnimeWhenSuccessful() {
        int expectedId = AnimeCreator.createValidAnime().getId();
        Anime animeToSave = AnimeCreator.createValidAnime();

        Anime returnedAnime = animeController.saveAnime(animeToSave).getBody();
        Assertions.assertThat(returnedAnime).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Removed Anime By Id when successful")
    public void deleteAnime_createsAnimeWhenSuccessful() {

        ResponseEntity<Anime> responseEntity = animeController.deleteAnime(1);
        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

}