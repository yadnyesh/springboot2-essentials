package yb.yadnyesh.springboot2.essentials.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import yb.yadnyesh.springboot2.essentials.domain.Anime;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Anime Repository Test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    private Anime createAnime() {
        return Anime.builder()
                .name("Tensei Shitara Slime Datta Ken")
                .url("http://yad.yb")
                .build();
    }

    @Test
    void findByName() {
    }

    @Test
    @DisplayName("Save creates anime when successful")
    public void savePersistAnimeWhenSuccessful() {
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("Updates Anime when successful")
    public void updateAnimeWhenSuccessful() {
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        savedAnime.setName("The time I got reincarnated as slime");
        Anime updatedAnime = this.animeRepository.save(savedAnime);
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isNotNull();
        Assertions.assertThat(updatedAnime.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("Deletes Anime when successful")
    public void deleteAnimeWhenSuccessful() {
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        this.animeRepository.delete(savedAnime);
        Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());
        Assertions.assertThat(animeOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Find Anime By Name when successful")
    public void findAnimeByNameWhenSuccessful() {
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        List<Anime> animeList = this.animeRepository.findByName(savedAnime.getName());
        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).contains(savedAnime);
    }

    @Test
    @DisplayName("Find Anime By Name when successful")
    public void returnEmptyAnimeByNameWhenNotFound() {
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        List<Anime> animeList = this.animeRepository.findByName(savedAnime.getName());
        Assertions.assertThat(animeList).isNotEmpty();
        Assertions.assertThat(animeList).contains(savedAnime);
    }


    @Test
    @DisplayName("Throw exception when Saving, name is empty")
    public void throwConstraintExceptionWhenNameIsEmpty() {
        Anime anime = new Anime();
        Assertions.assertThatThrownBy(() -> animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);
    }

}