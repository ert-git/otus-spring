package ru.otus.levina.hw01.output;

import ru.otus.levina.hw01.domain.Question;

public interface UserOutput {
    void printQuestion(Question question, int i);

    void printError(String msg);

    void printInfo(String msg);
}
