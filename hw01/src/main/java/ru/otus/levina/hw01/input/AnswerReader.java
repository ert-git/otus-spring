package ru.otus.levina.hw01.input;

import ru.otus.levina.hw01.domain.Answer;
import ru.otus.levina.hw01.domain.Question;
import ru.otus.levina.hw01.output.UserOutput;

public interface AnswerReader {
    Answer readAnswer(Question question);

    void close();
}
