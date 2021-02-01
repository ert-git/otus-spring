package ru.otus.levina.hw06.services;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

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
