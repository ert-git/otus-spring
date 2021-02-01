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

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String ID = "id";

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Author> rowMapper =
            (rs, rowNum) -> new Author(rs.getLong(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(MIDDLE_NAME));

    @Override
    public Optional<Author> getById(long id) {
        Map<String, Object> params = Map.of(ID, id);
        try {
            return Optional.ofNullable(jdbc
                    .queryForObject("select id, first_name, last_name, middle_name from authors where id = :id", params, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, first_name, last_name, middle_name from authors order by last_name", rowMapper);
    }

    @Override
    public void insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(FIRST_NAME, author.getFirstName())
                .addValue(LAST_NAME, author.getLastName())
                .addValue(MIDDLE_NAME, author.getMiddleName());
        jdbc
                .update("insert into authors (first_name, last_name, middle_name) values (:first_name, :last_name, :middle_name)", params, keyHolder);
        Long id = (Long) keyHolder.getKey();
        author.setId(id);
    }

    @Override
    public void delete(Author author) {
        jdbc.update("delete from authors where id = :id", Map.of(ID, author.getId()));
    }

    @Override
    public void update(Author author) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(ID, author.getId())
                .addValue(FIRST_NAME, author.getFirstName())
                .addValue(LAST_NAME, author.getLastName())
                .addValue(MIDDLE_NAME, author.getMiddleName());
        jdbc.update("update authors set first_name=:first_name, last_name=:last_name, middle_name=:middle_name where id = :id", params);
    }

    @Override
    public List<Author> getByIdList(List<Long> idList) {
        log.debug("getById: idList={}", idList);
        SqlParameterSource params = new MapSqlParameterSource("idList", idList);
        return jdbc.query("select id, first_name, last_name, middle_name from authors where id in (:idList)", params, rowMapper);
    }

    @Override
    public List<Author> getByBookId(long bookId) {
        log.debug("getByBookId: bookId={}", bookId);
        SqlParameterSource params = new MapSqlParameterSource("bookId", bookId);
        return jdbc.query("select a.id as id, first_name, last_name, middle_name from authors a join book_author on a.id = author_id where book_id = :bookId", params, rowMapper);
    }

}
