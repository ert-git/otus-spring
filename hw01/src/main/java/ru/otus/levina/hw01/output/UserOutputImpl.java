package ru.otus.levina.hw01.output;

import ru.otus.levina.hw01.domain.Question;
import static ru.otus.levina.hw01.core.Messages.*;

public class UserOutputImpl implements UserOutput {

    @Override
    public void printQuestion(Question question, int i) {
        System.out.println(String.format("%03d. %s? [%s]",
                i+1, question.getQuestion(), String.join(", ", question.getAnswers())));
    }

    @Override
    public void printError(String msg) {
        System.err.println(String.format("%s: %s", ERROR, msg));
    }

    @Override
    public void printInfo(String msg) {
        System.out.println(String.format("%s: %s", INFO, msg));
    }
}
