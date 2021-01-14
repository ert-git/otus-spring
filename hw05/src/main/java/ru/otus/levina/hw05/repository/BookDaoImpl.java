package ru.otus.levina.hw05.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.domain.Book;
import ru.otus.levina.hw05.domain.Genre;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {
    private static final String AUTHOR_ID = "authorId";
    private static final String TITLE = "title";
    private static final String BOOK_ID = "bookId";
    private static final String GENRE_ID = "genreId";
    private final static String SELECT_BOOK_SQL = String.format("select id, title from books where id = :%s", BOOK_ID);
    private final static String SELECT_AUTHOR_ID_SQL = String.format("select author_id from book_author where book_id = :%s", BOOK_ID);
    private final static String SELECT_GENRE_ID_SQL = String.format("select genre_id from book_genre where book_id = :%s", BOOK_ID);
    private static final String INSERT_BOOK_AUTHOR_SQL = String.format("insert into book_author (book_id, author_id) values (:%s, :%s)", BOOK_ID, AUTHOR_ID);
    private static final String INSERT_BOOK_GENRE_SQL = String.format("insert into book_genre (book_id, genre_id) values (:%s, :%s)", BOOK_ID, GENRE_ID);
    private static final String DELETE_BOOK_SQL = String.format("delete from books where id = :%s", BOOK_ID);
    private static final String DELETE_BOOK_GENRE_SQL = String.format("delete from book_genre where book_id = :%s and genre_id = :%s", BOOK_ID , GENRE_ID);
    private static final String DELETE_BOOK_AUTHOR_SQL = String.format("delete from book_author where book_id = :%s and  author_id = :%s", BOOK_ID, AUTHOR_ID);
    private static final String UPDATE_BOOK_SQL = String.format("update books set title =:title where id=:%s", BOOK_ID);
    public static final String INSERT_BOOK_SQL = String.format("insert into books (title) values (:%s)", TITLE);

    private final RowMapper<Book> rowMapper =
            (rs, rowNum) -> new Book(
                    rs.getLong("id"),
                    rs.getString(TITLE), null, null);

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Optional<Book> getById(long id) {
        try {
            Optional<Book> result = Optional.ofNullable(jdbc
                    .queryForObject(SELECT_BOOK_SQL, Collections.singletonMap(BOOK_ID, id), rowMapper));
            result
                    .ifPresent(book -> {
                       book.setAuthors(authorDao.getByIdList(getJoinedIdListByBookId(id, SELECT_AUTHOR_ID_SQL)));
                       book.setGenres(genreDao.getByIdList(getJoinedIdListByBookId(id, SELECT_GENRE_ID_SQL)));
                    });
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.ofNullable(null);
        }
    }

    private List<Long> getJoinedIdListByBookId(long id, String sql) {
        return jdbc.queryForList(sql, Collections.singletonMap(BOOK_ID, id), Long.class);
    }

    private void addAuthor(Author author, Book book) {
        if (author.getId() == 0) {
            authorDao.insert(author);
        }
        log.info("addAuthor: author={}, book={}", author, book);
        Map<String, Object> params = new HashMap<>();
        params.put(AUTHOR_ID, author.getId());
        params.put(BOOK_ID, book.getId());
        jdbc.update(INSERT_BOOK_AUTHOR_SQL, params);
    }

    private void addGenre(Genre genre, Book book) {
        if (genre.getId() == 0) {
            genreDao.insert(genre);
        }
        log.info("addGenre: book={}", book);
        Map<String, Object> params = new HashMap<>();
        params.put(GENRE_ID, genre.getId());
        params.put(BOOK_ID, book.getId());
        jdbc.update(INSERT_BOOK_GENRE_SQL, params);
    }

    @Override
    public Optional<Book> insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(TITLE, book.getTitle());
        jdbc.update(INSERT_BOOK_SQL, namedParameters, keyHolder);
        book.setId((Long) keyHolder.getKey());
        log.info("insert: inserted book={}", book);

        book.getAuthors().forEach(author -> addAuthor(author, book));
        book.getGenres().forEach(genre -> addGenre(genre, book));

        return getById(book.getId());
    }


    @Override
    public void delete(Book book) {
        book.getAuthors().forEach(a -> deleteAuthorsJoin(a, book));
        book.getGenres().forEach(g -> deleteGenresJoin(g, book));
        jdbc.update(DELETE_BOOK_SQL, Collections.singletonMap(BOOK_ID, book.getId()));
    }

    private void deleteGenresJoin(Genre genre, Book book) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(BOOK_ID, book.getId())
                .addValue(GENRE_ID, genre.getId());
        jdbc.update(DELETE_BOOK_GENRE_SQL, namedParameters);
    }

    private void deleteAuthorsJoin(Author author, Book book) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(BOOK_ID, book.getId())
                .addValue(AUTHOR_ID, author.getId());
        jdbc.update(DELETE_BOOK_AUTHOR_SQL, namedParameters);
    }

    @Override
    public void update(Book book) {
        Book oldBook = getById(book.getId()).get();
        Map<String, Object> params = new HashMap<>();
        params.put(TITLE, book.getTitle());
        params.put(BOOK_ID, book.getId());
        jdbc.update(UPDATE_BOOK_SQL, params);
        oldBook.getAuthors().stream().filter(oldAuthor -> !book.getAuthors().contains(oldAuthor)).forEach(oldAuthor -> deleteAuthorsJoin(oldAuthor, book));
        book.getAuthors().stream().filter(newAuthor -> !oldBook.getAuthors().contains(newAuthor)).forEach(newAuthor -> addAuthor(newAuthor, book));
        oldBook.getGenres().stream().filter(oldGenre -> !book.getGenres().contains(oldGenre)).forEach(oldGenre -> deleteGenresJoin(oldGenre, book));
        book.getGenres().stream().filter(newGenre -> !oldBook.getGenres().contains(newGenre)).forEach(newGenre -> addGenre(newGenre, book));
    }


    @Override
    public List<Author> getBookAuthors(long bookId) {
       return authorDao.getByIdList(jdbc.queryForList(SELECT_AUTHOR_ID_SQL, Collections.singletonMap(BOOK_ID, bookId), Long.class));
    }

    @Override
    public List<Genre> getBookGenres(long bookId) {
        return genreDao.getByIdList(jdbc.queryForList(SELECT_GENRE_ID_SQL, Collections.singletonMap(BOOK_ID, bookId), Long.class));
    }

}
