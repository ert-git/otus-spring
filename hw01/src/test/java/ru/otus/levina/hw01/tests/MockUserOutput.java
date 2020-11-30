package ru.otus.levina.hw01.tests;

import lombok.Getter;
import ru.otus.levina.hw01.domain.Question;
import ru.otus.levina.hw01.output.UserOutput;

import java.util.ArrayList;
import java.util.List;

public class MockUserOutput implements UserOutput {
    @Getter
    private final List<String> questions = new ArrayList<>();
    @Getter
    private final List<String> errors = new ArrayList<>();
    @Getter
    private final List<String> infos = new ArrayList<>();

    @Override
    public void printQuestion(Question question, int i) {
        this.questions.add(question.toString());
    }

    @Override
    public void printError(String msg) {
        this.errors.add(msg);
    }
    @Override
    public void printInfo(String msg) {
        this.infos.add(msg);
    }
}
