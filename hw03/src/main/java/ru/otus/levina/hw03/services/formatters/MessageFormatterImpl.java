package ru.otus.levina.hw03.services.formatters;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.common.Messages;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;
import ru.otus.levina.hw03.services.core.MessageService;

import static ru.otus.levina.hw03.common.Messages.*;

@AllArgsConstructor
@Service
public class MessageFormatterImpl implements MessageFormatter {

    private final MessageService messageService;

    @Override
    public String formatQuestion(Question question) {
        String index = question.getId() > 0 ? String.format("%03d. ", question.getId()) : "";
        String choices = question.getChoices().isEmpty() ? "" : String.format(" [%s]", String.join(", ", question.getChoices()));
        return String.format("%s%s%s", index, question.getQuestion(), choices);
    }

    @Override
    public String formatError(String msg) {
        return String.format("%s", messageService.getMessage(Messages.ERROR, msg));
    }

    @Override
    public String formatResult(TestResult result) {
        return messageService.getMessage(INFO_RESULT,
                result.getAnswers().stream().filter(a -> a.isCorrect()).count(),
                result.getAnswers().size())
                + " "
                + messageService.getMessage(result.isPassed() ? TEST_PASSED : TEST_FAILED)
                ;
    }

}
