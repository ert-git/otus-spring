package ru.otus.levina.hw05.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.domain.Book;
import ru.otus.levina.hw05.domain.Genre;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {
    private static final String AUTHOR_ID = "authorId";
    private static final String TITLE = "title";
    private static final String BOOK_ID = "bookId";
    private static final String GENRE_ID = "genreId";

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
                    .queryForObject("select id, title from books where id = :bookId", Collections.singletonMap(BOOK_ID, id), rowMapper));
            result
                    .ifPresent(book -> {
                        book.setAuthors(authorDao.getByBookId(id));
                        book.setGenres(genreDao.getByBookId(id));
                    });
            return result;
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getByPage(int startRowNum, int rowCount) {
        // В общем случае реализация будет зависит оттого, у кого больше ресурсов (у базы или у явы), и зачем нам вообще такой метод.
        // Если это какой-то каталог, то он будет выводить книги постранично или группами по автору/жанру и т.п.
        // Но точно не все, что есть в базе.
        // Если нам нужно максимальное быстродействие, то есть смысл подумать об избыточности базы (денормализации) и/или кэшировании жанров/авторов на бэке.
        // Тогда сможем выдернуть книги (вместе с жанрами и авторами) и за один запрос.
        // Конечно, один мегазапрос со всеми join можно сделать и здесь, но тогда порушится вся концепция деления на репозитории.
        // Оптимальным вариантом видится нечто среднее.
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("startRowNum", startRowNum)
                .addValue("rowCount", rowCount);
        List<Book> books = jdbc.query("select id, title from books limit :startRowNum, :rowCount", params, rowMapper);
        List<Long> bookIds = books.stream().map(Book::getId).collect(Collectors.toList());
        Map<Long, List<Author>> authors = authorDao.getByBookIdList(bookIds);
        Map<Long, List<Genre>> genres = genreDao.getByBookIdList(bookIds);
        books.forEach(b -> {
            b.setAuthors(authors.getOrDefault(b.getId(), Collections.EMPTY_LIST));
            b.setGenres(genres.getOrDefault(b.getId(), Collections.EMPTY_LIST));
        });
        return books;
    }

    private void joinAuthorsToBook(long bookId, List<Long> authorIds) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(authorIds.stream()
                .map(id -> {
                    Map<String, Long> map = new HashMap<>();
                    map.put(BOOK_ID, bookId);
                    map.put(AUTHOR_ID, id);
                    return map;
                }).toArray());
        log.info("joinAuthorsToBook: bookId={}, authorIds={}", bookId, authorIds);
        jdbc.batchUpdate("insert into book_author (book_id, author_id) values (:bookId, :authorId)", batch);
    }

    private void joinGenresToBook(long bookId, List<Long> genreIds) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(genreIds.stream()
                .map(id -> {
                    Map<String, Long> map = new HashMap<>();
                    map.put(BOOK_ID, bookId);
                    map.put(GENRE_ID, id);
                    return map;
                }).toArray());
        jdbc.batchUpdate("insert into book_genre (book_id, genre_id) values (:bookId, :genreId)", batch);
    }

    @Override
    public Optional<Book> insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(TITLE, book.getTitle());
        jdbc.update("insert into books (title) values (:title)", namedParameters, keyHolder);
        book.setId((Long) keyHolder.getKey());
        log.info("insert: inserted book={}", book);

        book.getAuthors().stream().filter(a -> a.getId() == 0).forEach(authorDao::insert);
        joinAuthorsToBook(book.getId(), book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));

        book.getGenres().stream().filter(a -> a.getId() == 0).forEach(genreDao::insert);
        joinGenresToBook(book.getId(), book.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));

        return getById(book.getId());
    }


    @Override
    public void delete(Book book) {
        deleteAuthorsJoin(book.getId(), book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
        deleteGenresJoin(book.getId(), book.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));
        jdbc.update("delete from books where id = :bookId", Collections.singletonMap(BOOK_ID, book.getId()));
    }

    private void deleteGenresJoin(long bookId, List<Long> genreIds) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("idList", genreIds)
                .addValue("bookId", bookId);
        jdbc.update("delete from book_genre where book_id = :bookId and genre_id in (:idList)", params);
    }

    private void deleteAuthorsJoin(long bookId, List<Long> authorIds) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("idList", authorIds)
                .addValue("bookId", bookId);
        jdbc.update("delete from book_author where book_id = :bookId and author_id in (:idList)", params);
    }

    @Override
    public void update(Book book) {
        Book oldBook = getById(book.getId()).get();
        Map<String, Object> params = new HashMap<>();
        params.put(TITLE, book.getTitle());
        params.put(BOOK_ID, book.getId());
        jdbc.update("update books set title =:title where id=:bookId", params);
        book.getAuthors().stream().filter(a -> a.getId() == 0).forEach(authorDao::insert);
        book.getGenres().stream().filter(a -> a.getId() == 0).forEach(genreDao::insert);
        joinAuthorsToBook(book.getId(),  book.getAuthors().stream().filter(newAuthor -> !oldBook.getAuthors().contains(newAuthor)).map(Author::getId).collect(Collectors.toList()));
        joinGenresToBook(book.getId(),  book.getGenres().stream().filter(newGenre -> !oldBook.getGenres().contains(newGenre)).map(Genre::getId).collect(Collectors.toList()));
        deleteAuthorsJoin(book.getId(), oldBook.getAuthors().stream().filter(oldAuthor -> !book.getAuthors().contains(oldAuthor)).map(Author::getId).collect(Collectors.toList()));
        deleteGenresJoin(book.getId(), oldBook.getGenres().stream().filter(oldGenre -> !book.getGenres().contains(oldGenre)).map(Genre::getId).collect(Collectors.toList()));
    }


    @Override
    public List<Author> getBookAuthors(long bookId) {
        return authorDao.getByIdList(jdbc.queryForList("select author_id from book_author where book_id = :bookId", Collections.singletonMap(BOOK_ID, bookId), Long.class));
    }

    @Override
    public List<Genre> getBookGenres(long bookId) {
        return genreDao.getByIdList(jdbc.queryForList("select genre_id from book_genre where book_id = :bookId", Collections.singletonMap(BOOK_ID, bookId), Long.class));
    }

}
