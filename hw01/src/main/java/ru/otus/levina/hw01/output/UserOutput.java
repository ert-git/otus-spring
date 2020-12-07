package ru.otus.levina.hw01.output;

import ru.otus.levina.hw01.domain.Answer;
import ru.otus.levina.hw01.domain.Question;

import java.util.List;

public interface UserOutput {
    void printQuestion(Question question, int i);

    void printError(String msg);

    void printResult(List<Answer> answers);
}
