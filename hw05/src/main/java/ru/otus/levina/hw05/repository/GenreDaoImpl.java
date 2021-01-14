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
    public static final String SELECT_BY_ID_SQL = String.format("select %s, %s, from genres where %s = :%s", ID, NAME, ID, ID);
    public static final String SELECT_ALL_SQL = String.format("select %s, %s, from genres order by %s", ID, NAME, NAME);
    public static final String INSERT_SQL = String.format("insert into genres (%s) values (:%s)", NAME, NAME);
    public static final String DELETE_BY_ID = String.format("delete from genres where %s = :%s", ID, ID);
    public static final String UPDATE_SQL = String.format("update genres set %s=:%s where %s = :%s", NAME, NAME, ID, ID);

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Genre> rowMapper =
            (rs, rowNum) -> new Genre(rs.getLong(ID),
                    rs.getString(NAME));

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(jdbc.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap(ID, id), rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query(SELECT_ALL_SQL, rowMapper);
    }

    @Override
    public Optional<Genre> insert(Genre genre) {
        log.info("insert: genre={}", genre);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(NAME, genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(INSERT_SQL, namedParameters, keyHolder);
        Long id = (Long) keyHolder.getKey();
        genre.setId(id);
        log.info("insert: inserted genre={}", genre);
        return getById(id);
    }

    @Override
    public void delete(Genre genre) {
        jdbc.update(DELETE_BY_ID, Collections.singletonMap(ID, genre.getId()));
    }

    @Override
    public void update(Genre genre) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue(NAME, genre.getName())
                .addValue(ID, genre.getId());
        jdbc.update(UPDATE_SQL, namedParameters);
    }

    @Override
    public List<Genre> getByIdList(List<Long> idList) {
        return idList.stream().map(id -> getById(id).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
