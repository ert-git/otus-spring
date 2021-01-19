package ru.otus.levina.hw05.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.repository.AuthorDao;
import ru.otus.levina.hw05.repository.AuthorDaoImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("AuthorDaoTest")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("find author by id")
    @Test
    void testAuthorById() {
        int id = 1001;
        Author actualAuthor = authorDao.getById(id).orElse(null);
        assertNotNull(actualAuthor);
        Author expectedAuthor = new Author(id, "Александр", "Пушкин", "Сергеевич");
        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("find author by not existed id")
    @Test
    void testNoAuthorById() {
        int id = 1001000;
        Author actualAuthor = authorDao.getById(id).orElse(null);
        assertNull(actualAuthor);
    }

    @DisplayName("get all authors")
    @Test
    void testGetAllAuthors() {
        List<Author> actualAuthors = authorDao.getAll();
        List<Author> expectedAuthors = List.of(
                new Author(1002, "Гомер", null, null),
                new Author(1001, "Александр", "Пушкин", "Сергеевич"),
                new Author(1003, "a1003", null, null),
                new Author(1004, "a1004", null, null),
                new Author(1005, "a1005", null, null));
        assertEquals(expectedAuthors.size(), actualAuthors.size());
        assertEquals(expectedAuthors.stream().filter(actualAuthors::contains).count(), expectedAuthors.size());
    }

    @DisplayName("get authors by id list")
    @Test
    void testGetAuthorsByIdList() {
        List<Author> actualAuthors = authorDao.getByIdList(List.of(1002L, 1001L));
        List<Author> expectedAuthors = List.of(
                new Author(1002, "Гомер", null, null),
                new Author(1001, "Александр", "Пушкин", "Сергеевич"));
        assertEquals(expectedAuthors.size(), actualAuthors.size());
        assertEquals(expectedAuthors.stream().filter(actualAuthors::contains).count(), expectedAuthors.size());
    }

    @DisplayName("get authors by book id")
    @Test
    void testGetAuthorsByBookId() {
        List<Author> actualAuthors = authorDao.getByBookId(1001L);
        List<Author> expectedAuthors = List.of(
                new Author(1001, "Александр", "Пушкин", "Сергеевич"));
        assertEquals(expectedAuthors.size(), actualAuthors.size());
        assertEquals(expectedAuthors.stream().filter(actualAuthors::contains).count(), expectedAuthors.size());
    }


    @DisplayName("get authors by book id list")
    @Test
    void testGetAuthorsByBookIdList() {
        List<Long> bookIds = List.of(1003L, 1004L, 1005L);
        Map<Long, List<Author>> actualAuthors = authorDao.getByBookIdList(bookIds);
        actualAuthors.entrySet().forEach(e -> log.info("entry: {}", e));
        Map<Long, List<Author>> expectedAuthors = new HashMap<>();
        bookIds.forEach(id -> expectedAuthors.putIfAbsent(id, new ArrayList<>()));
        expectedAuthors.get(1003L).add(new Author(1003, "a1003", null, null));
        expectedAuthors.get(1003L).add(new Author(1004, "a1004", null, null));
        expectedAuthors.get(1004L).add(new Author(1004, "a1004", null, null));
        expectedAuthors.get(1004L).add(new Author(1005, "a1005", null, null));
        expectedAuthors.get(1005L).add(new Author(1003, "a1003", null, null));
        assertEquals(expectedAuthors, actualAuthors);
    }

    @DisplayName("add an author")
    @Test
    void testAddAuthor() {
        Author expectedAuthor = new Author("Fn1", "Ln1", "Mn1");
        Long id = authorDao.insert(expectedAuthor).map(Author::getId).orElse(null);
        assertNotNull(id);
        Author actualAuthor = authorDao.getById(id).orElse(null);
        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("delete an author")
    @Test
    void testDeleteAuthor() {
        Author expectedAuthor = new Author("Fn2", "Ln2", "Mn2");
        Long id = authorDao.insert(expectedAuthor).map(Author::getId).orElse(null);
        assertNotNull(id);
        Author actualAuthor = authorDao.getById(id).orElse(null);
        assertEquals(expectedAuthor, actualAuthor);
        authorDao.delete(actualAuthor);
        actualAuthor = authorDao.getById(id).orElse(null);
        assertNull(actualAuthor);
    }

    @DisplayName("udpate an author")
    @Test
    void testUpdateAuthor() {
        Author origAuthor = new Author("Fn3", "Ln3", "Mn3");
        Long id = authorDao.insert(origAuthor).map(Author::getId).orElse(null);
        assertNotNull(id);
        Author actualAuthor = authorDao.getById(id).orElse(null);
        assertEquals(origAuthor, actualAuthor);

        origAuthor.setFirstName("Fn4");
        origAuthor.setLastName("Fn4");
        origAuthor.setMiddleName("Fn4");

        authorDao.update(origAuthor);
        actualAuthor = authorDao.getById(id).orElse(null);
        assertEquals(origAuthor, actualAuthor);
    }
}
