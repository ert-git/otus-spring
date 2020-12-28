package ru.otus.levina.hw02.repository;

import ru.otus.levina.hw02.domain.Question;

import java.util.List;

public interface QuestionsRepository {
	List<Question> loadQuestions() throws QuestionsRepositoryException;
}
