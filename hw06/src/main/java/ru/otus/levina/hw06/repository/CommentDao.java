package ru.otus.levina.hw06.repository;

import ru.otus.levina.hw06.domain.Book;
import ru.otus.levina.hw06.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

  Optional<Comment> getById(long id);

  List<Comment> getAll(Book book);

  void delete(Comment author);

  void update(Comment author);

}
