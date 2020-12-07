package ru.otus.levina.hw01.input;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
