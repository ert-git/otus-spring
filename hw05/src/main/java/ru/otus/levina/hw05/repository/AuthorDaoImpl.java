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
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {

    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String ID = "id";
    public static final String SELECT_BY_ID_SQL = String.format("select %s, %s, %s, %s from authors where %s = :%s",
            ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, ID, ID);
    public static final String SELECT_ALL_SQL = String.format("select %s, %s, %s, %s from authors order by %s",
            ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, LAST_NAME);
    public static final String INSERT_SQL = String.format("insert into authors (%s, %s, %s) values (:%s, :%s, :%s)",
            FIRST_NAME, LAST_NAME, MIDDLE_NAME, FIRST_NAME, LAST_NAME, MIDDLE_NAME);
    public static final String DELETE_BY_ID = String.format("delete from authors where %s = :%s", ID, ID);
    public static final String UPDATE_SQL = String.format("update authors set %s=:%s, %s=:%s, %s=:%s where %s = :%s",
            FIRST_NAME, LAST_NAME, MIDDLE_NAME, FIRST_NAME, LAST_NAME, MIDDLE_NAME, ID, ID);

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Author> rowMapper =
            (rs, rowNum) -> new Author(rs.getLong(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getString(MIDDLE_NAME));

    @Override
    public Optional<Author> getById(long id) {
        Map<String, Object> params = Collections.singletonMap(ID, id);
        try {
            return Optional.ofNullable(jdbc
                    .queryForObject(SELECT_BY_ID_SQL, params, rowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query(SELECT_ALL_SQL, rowMapper);
    }

    @Override
    public Optional<Author> insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(FIRST_NAME, author.getFirstName())
                .addValue(LAST_NAME, author.getLastName())
                .addValue(MIDDLE_NAME, author.getMiddleName());
        jdbc
                .update(INSERT_SQL, params, keyHolder);
        Long id = (Long) keyHolder.getKey();
        author.setId(id);
        return getById(id);
    }

    @Override
    public void delete(Author author) {
        jdbc.update(DELETE_BY_ID, Collections.singletonMap(ID, author.getId()));
    }

    @Override
    public void update(Author author) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(ID, author.getId())
                .addValue(FIRST_NAME, author.getFirstName())
                .addValue(LAST_NAME, author.getLastName())
                .addValue(MIDDLE_NAME, author.getMiddleName());
        jdbc.update(UPDATE_SQL, params);
    }

    @Override
    public List<Author> getByIdList(List<Long> idList) {
        log.debug("getById: idList={}", idList);
        return idList.stream().map(id -> getById(id).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
