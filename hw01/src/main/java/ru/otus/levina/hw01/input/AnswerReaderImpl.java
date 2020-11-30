package ru.otus.levina.hw01.input;

import ru.otus.levina.hw01.domain.Answer;
import ru.otus.levina.hw01.domain.Question;
import ru.otus.levina.hw01.output.UserOutput;

import static ru.otus.levina.hw01.core.Messages.ERROR_EMPTY_INPUT;

public class AnswerReaderImpl implements AnswerReader {
    private InputReader inputReader;
    private UserOutput userOutput;

    public AnswerReaderImpl(InputReader inputReader, UserOutput userOutput) {
        this.inputReader = inputReader;
        this.userOutput = userOutput;
    }

    @Override
    public Answer readAnswer(Question question) {
        String userinput = inputReader.nextLine();
        while (userinput.isEmpty()) {
            userOutput.printError(ERROR_EMPTY_INPUT);
            userinput = inputReader.nextLine();
        }
        return new Answer(question, userinput);
    }

    @Override
    public void close() {
        inputReader.close();
    }

}
