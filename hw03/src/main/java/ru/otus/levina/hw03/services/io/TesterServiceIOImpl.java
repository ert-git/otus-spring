package ru.otus.levina.hw03.services.io;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.domain.Answer;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;
import ru.otus.levina.hw03.services.formatters.MessageFormatter;

import static ru.otus.levina.hw03.common.Messages.ERROR_EMPTY_INPUT;

@Service
@AllArgsConstructor
public class TesterServiceIOImpl implements TesterServiceIO {
    private final UserIOFacade io;
    private final MessageFormatter formatter;

    @Override
    public Answer getAnswer(Question question) {
        String userinput = io.readNotEmptyString(formatter.formatQuestion(question), ERROR_EMPTY_INPUT);
        return new Answer(question, userinput);
    }

    @Override
    public void printResult(TestResult result) {
        io.print(formatter.formatResult(result));
    }


}
