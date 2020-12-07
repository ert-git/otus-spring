package ru.otus.levina.hw01.sources;

import ru.otus.levina.hw01.domain.Question;

import java.util.List;

public interface QuestionsSource {
	List<Question> loadQuestions() throws QuestionsSourceException;
}
