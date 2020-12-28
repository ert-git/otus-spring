package ru.otus.levina.hw02.services.formatters;

import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;

public interface MessageFormatter {
    String formatQuestion(Question question);
    String formatError(String msg);
    String formatResult(TestResult result);
}
