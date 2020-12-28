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

public interface TesterServiceIO {
     void printQuestion(Question q);
     Answer readAnswer(Question question) ;
     void printResult(TestResult result);
}
