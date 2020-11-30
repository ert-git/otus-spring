package ru.otus.levina.hw01.tests;

import ru.otus.levina.hw01.input.InputReader;

public class MockInputReader implements InputReader {

    private String[] answers;
    private int pointer =0;

    public MockInputReader(String[] answers) {
        this.answers = answers;
    }

    @Override
    public String nextLine() {
        return answers[pointer++];
    }

    @Override
    public void close() {

    }
}
