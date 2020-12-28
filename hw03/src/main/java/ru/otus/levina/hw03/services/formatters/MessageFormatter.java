package ru.otus.levina.hw03.services.formatters;

import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;

public interface MessageFormatter {
    String formatQuestion(Question question);
    String formatError(String msg);
    String formatResult(TestResult result);
}
