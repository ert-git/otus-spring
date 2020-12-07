package ru.otus.levina.hw02.services.io;

import ru.otus.levina.hw02.domain.Answer;
import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;

public interface UserIO {
    void printQuestion(Question q);
    Answer readAnswer(Question question);
    void printResult(TestResult result);
}
