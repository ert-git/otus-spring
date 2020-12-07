package ru.otus.levina.hw02.services.formatters;

import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;

import static ru.otus.levina.hw02.common.Messages.*;


public class ConsoleMessageFormatter implements MessageFormatter {

    @Override
    public String formatQuestion(Question question) {
        String index = question.getId() > 0 ? String.format("%03d. ", question.getId()) : "";
        String choices = question.getChoices().isEmpty() ? "" : String.format(" [%s]", String.join(", ", question.getChoices()));
        return String.format("%s%s%s", index, question.getQuestion(), choices);
    }

    @Override
    public String formatError(String msg) {
        return String.format("%s", msg);
    }

    @Override
    public String formatResult(TestResult result) {
        return String.format(INFO_RESULT,
                result.getAnswers().stream().filter(a -> a.isCorrect()).count(),
                result.getAnswers().size())
                + " "
                + (result.isPassed() ? TEST_PASSED : TEST_FAILED)
                ;
    }

}
