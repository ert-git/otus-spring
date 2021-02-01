package ru.otus.levina.hw06.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.otus.levina.hw06.domain.Author;
import ru.otus.levina.hw06.domain.Book;
import ru.otus.levina.hw06.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentDaoImpl implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> getById(long id) {
        try {
            return Optional.ofNullable(em.find(Comment.class, id));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Comment> getAll(Book book) {
        return null;
    }


    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }

}
