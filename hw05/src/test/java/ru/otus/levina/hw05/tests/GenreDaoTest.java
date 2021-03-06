package ru.otus.levina.hw05.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.levina.hw05.domain.Genre;
import ru.otus.levina.hw05.repository.GenreDao;
import ru.otus.levina.hw05.repository.GenreDaoImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("GenreDaoTest")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @DisplayName("find genre by id")
    @Test
    void testGenreById() {
        int id = 1001;
        Genre actualgenre = genreDao.getById(id).orElse(null);
        assertNotNull(actualgenre);
        Genre expectedgenre = new Genre(id, "Античная литература");
        assertEquals(expectedgenre, actualgenre);
    }

    @DisplayName("find genre by not existed id")
    @Test
    void testNoGenreById() {
        int id = 1001000;
        Genre actualgenre = genreDao.getById(id).orElse(null);
        assertNull(actualgenre);
    }

    @DisplayName("get all genres")
    @Test
    void testGetAllGenres() {
        List<Genre> actualgenres = genreDao.getAll();
        List<Genre> expectedgenres = List.of(
                new Genre(1001, "Античная литература"),
                new Genre(1002, "Русская классика"),
                new Genre(1003, "g1003"),
                new Genre(1004, "g1004")
        );
        assertEquals(expectedgenres.size(), actualgenres.size());
        assertEquals(expectedgenres.stream().filter(actualgenres::contains).count(), expectedgenres.size());
    }

    @DisplayName("get genres by id list")
    @Test
    void testGetGenresByIdList() {
        List<Genre> actualgenres = genreDao.getByIdList(List.of(1001L, 1002L));
        List<Genre> expectedgenres = List.of(
                new Genre(1001, "Античная литература"),
                new Genre(1002, "Русская классика"));
        assertEquals(expectedgenres.size(), actualgenres.size());
        assertEquals(expectedgenres.stream().filter(actualgenres::contains).count(), expectedgenres.size());
    }

    @DisplayName("get genres by book id")
    @Test
    void testGetGenresByBookId() {
        List<Genre> actualgenres = genreDao.getByBookId(1001);
        List<Genre> expectedgenres = List.of(
                new Genre(1002, "Русская классика"));
        assertEquals(expectedgenres.size(), actualgenres.size());
        assertEquals(expectedgenres.stream().filter(actualgenres::contains).count(), expectedgenres.size());
    }

    @DisplayName("add an genre")
    @Test
    void testAddGenre() {
        Genre expectedgenre = new Genre("G1");
        genreDao.insert(expectedgenre);
        long id = expectedgenre.getId();
        assertTrue(id > 0);
        Genre actualgenre = genreDao.getById(id).orElse(null);
        assertEquals(expectedgenre, actualgenre);
        genreDao.delete(actualgenre);
    }

    @DisplayName("delete an genre")
    @Test
    void testDeleteGenre() {
        Genre expectedgenre = new Genre("G2");
        genreDao.insert(expectedgenre);
        long id = expectedgenre.getId();
        assertTrue(id > 0);
        Genre actualgenre = genreDao.getById(id).orElse(null);
        assertEquals(expectedgenre, actualgenre);
        genreDao.delete(actualgenre);
        actualgenre = genreDao.getById(id).orElse(null);
        assertNull(actualgenre);
    }

    @DisplayName("udpate an genre")
    @Test
    void testUpdateGenre() {
        Genre origgenre = new Genre("G3");
        genreDao.insert(origgenre);
        long id = origgenre.getId();
        assertTrue(id > 0);
        Genre actualgenre = genreDao.getById(id).orElse(null);
        assertEquals(origgenre, actualgenre);

        origgenre.setName("G4");

        genreDao.update(origgenre);
        actualgenre = genreDao.getById(id).orElse(null);
        assertEquals(origgenre, actualgenre);
    }
}
