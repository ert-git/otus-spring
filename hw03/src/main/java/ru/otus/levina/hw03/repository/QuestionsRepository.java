package ru.otus.levina.hw03.repository;

import ru.otus.levina.hw03.domain.Question;

import java.util.List;

public interface QuestionsRepository {
	List<Question> getQuestions() throws QuestionsRepositoryException;
}
