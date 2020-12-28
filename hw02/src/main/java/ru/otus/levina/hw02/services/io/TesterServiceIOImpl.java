package ru.otus.levina.hw02.services.io;

import ru.otus.levina.hw02.domain.Answer;
import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;
import ru.otus.levina.hw02.services.formatters.MessageFormatter;

import static ru.otus.levina.hw02.common.Messages.ERROR_EMPTY_INPUT;

public class TesterServiceIOImpl implements TesterServiceIO {
    private final UserIO io;
    private final MessageFormatter formatter;

    public TesterServiceIOImpl(UserIO io, MessageFormatter formatter) {
        this.io = io;
        this.formatter = formatter;
    }

    @Override
    public void printQuestion(Question q) {
        io.print(formatter.formatQuestion(q));
    }

    @Override
    public Answer readAnswer(Question question) {
        String userinput = io.read();
        while (userinput.isEmpty()) {
            io.print(formatter.formatError(ERROR_EMPTY_INPUT));
            userinput = io.read();
        }
        return new Answer(question, userinput);
    }

    @Override
    public void printResult(TestResult result) {
        io.print(formatter.formatResult(result));
    }


}
