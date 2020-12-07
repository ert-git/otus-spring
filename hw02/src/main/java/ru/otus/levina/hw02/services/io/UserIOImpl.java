package ru.otus.levina.hw02.services.io;

import ru.otus.levina.hw02.domain.Answer;
import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;
import ru.otus.levina.hw02.services.formatters.MessageFormatter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static ru.otus.levina.hw02.common.Messages.ERROR_EMPTY_INPUT;

public class UserIOImpl implements UserIO {
    private final Scanner in;
    private final PrintStream out;
    private final MessageFormatter formatter;

    public UserIOImpl(InputStream in, OutputStream out, MessageFormatter formatter) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.formatter = formatter;
    }

    @Override
    public void printQuestion(Question q) {
        out.println(formatter.formatQuestion(q));
    }

    @Override
    public Answer readAnswer(Question question) {
        String userinput = in.nextLine();
        while (userinput.isEmpty()) {
            out.println(formatter.formatError(ERROR_EMPTY_INPUT));
            userinput = in.nextLine();
        }
        return new Answer(question, userinput);
    }

    @Override
    public void printResult(TestResult result) {
        out.println(formatter.formatResult(result));
    }


}
