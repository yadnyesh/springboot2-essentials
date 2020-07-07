package yb.yadnyesh.springboot2.essentials.service;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import yb.yadnyesh.springboot2.essentials.domain.Anime;
import yb.yadnyesh.springboot2.essentials.exception.ResourceNotFoundException;
import yb.yadnyesh.springboot2.essentials.repository.AnimeRepository;
import yb.yadnyesh.springboot2.essentials.util.AnimeCreator;
import yb.yadnyesh.springboot2.essentials.util.Utils;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {


    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @Mock
    private Utils utils;

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
        BDDMockito.when(utils.findAnimeOrThrowNotFound(ArgumentMatchers.anyInt()))
                .thenReturn(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("List All returns a pageable list of Animes when successful")
    public void listAllAnime() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAllAnime(PageRequest.of(1,1));
        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty();
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Returns an Anime By Id when successful")
    public void findById_returnsAnimeWhenSuccessful() {
        int expectedId = AnimeCreator.createValidAnime().getId();
        Anime returnedAnime = animeService.findById(1);
        Assertions.assertThat(returnedAnime).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Saves an Anime By Id when successful")
    public void saveAnime_createsAnimeWhenSuccessful() {
        int expectedId = AnimeCreator.createValidAnime().getId();
        Anime animeToSave = AnimeCreator.createValidAnime();

        Anime returnedAnime = animeService.save(animeToSave);
        Assertions.assertThat(returnedAnime).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isNotNull();
        Assertions.assertThat(returnedAnime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Delete Anime By Id when successful")
    public void deleteAnime_createsAnimeWhenSuccessful() {

        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete Anime Throws ResourceNotFound By Id when Anime does not exist")
    public void deleteAnime_throwsResourceNotFoundWhenSuccessful() {
        BDDMockito.when(utils.findAnimeOrThrowNotFound(ArgumentMatchers.anyInt()))
                .thenThrow(new ResourceNotFoundException("Anime Not Found"));
        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> animeService.delete(1));
    }


    @Test
    @DisplayName("Updates an Anime By Id when successful")
    public void updateAnime_createsAnimeWhenSuccessful() {

        Anime validUpdatedAnime= AnimeCreator.createValidUpdatedAnime();
        String expectedName = validUpdatedAnime.getName();

        Anime expectedAnimeName = animeService.save(AnimeCreator.createValidUpdatedAnime());

        Assertions.assertThat(expectedAnimeName).isNotNull();
        Assertions.assertThat(expectedAnimeName.getName()).isEqualTo(expectedName);
    }

}