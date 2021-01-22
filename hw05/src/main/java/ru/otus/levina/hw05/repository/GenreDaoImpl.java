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
import ru.otus.levina.hw05.domain.Genre;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreDaoImpl implements GenreDao {

    public static final String NAME = "name";
    public static final String ID = "id";

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Genre> rowMapper =
            (rs, rowNum) -> new Genre(rs.getLong(ID),
                    rs.getString(NAME));

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject("select id, name, from genres where id = :id", Map.of(ID, id), rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name, from genres order by name", rowMapper);
    }

    @Override
    public void insert(Genre genre) {
        log.info("insert: genre={}", genre);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(NAME, genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres (name) values (:name)", namedParameters, keyHolder);
        Long id = (Long) keyHolder.getKey();
        genre.setId(id);
        log.info("insert: inserted genre={}", genre);
    }

    @Override
    public void delete(Genre genre) {
        jdbc.update("delete from genres where id = :id", Map.of(ID, genre.getId()));
    }

    @Override
    public void update(Genre genre) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(NAME, genre.getName())
                .addValue(ID, genre.getId());
        jdbc.update("update genres set name=:name where id = :id", namedParameters);
    }

    @Override
    public List<Genre> getByIdList(List<Long> idList) {
        log.debug("getById: idList={}", idList);
        SqlParameterSource params = new MapSqlParameterSource("idList", idList);
        return jdbc.query("select id, name from genres where id in (:idList)", params, rowMapper);
    }

    @Override
    public List<Genre> getByBookId(long bookId) {
        log.debug("getByBookId: bookId={}", bookId);
        SqlParameterSource params = new MapSqlParameterSource("bookId", bookId);
        return jdbc.query("select g.id as id, name from genres g join book_genre on g.id = genre_id where book_id = :bookId", params, rowMapper);
    }

}
