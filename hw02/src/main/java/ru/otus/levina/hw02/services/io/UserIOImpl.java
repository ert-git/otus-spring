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

    public UserIOImpl(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
    }

    @Override
    public void print(String msg) {
        out.println(msg);
    }

    @Override
    public String read() {
        return in.nextLine();
    }
}
