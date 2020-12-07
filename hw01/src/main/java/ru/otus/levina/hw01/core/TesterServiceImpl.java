package ru.otus.levina.hw01.core;

import lombok.extern.slf4j.Slf4j;
import ru.otus.levina.hw01.domain.Answer;
import ru.otus.levina.hw01.domain.Question;
import ru.otus.levina.hw01.input.AnswerReader;
import ru.otus.levina.hw01.output.UserOutput;
import ru.otus.levina.hw01.sources.QuestionsSource;
import ru.otus.levina.hw01.sources.QuestionsSourceException;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.levina.hw01.core.Messages.INFO_RESULT;

@Slf4j
public class TesterServiceImpl {

    private final QuestionsSource questionsReader;
    private final AnswerReader answerReader;
    private final UserOutput userOutput;

    public TesterServiceImpl(QuestionsSource questionsReader, AnswerReader answerReader, UserOutput userOutput) {
        this.questionsReader = questionsReader;
        this.answerReader = answerReader;
        this.userOutput = userOutput;
    }

    public List<Answer> start() throws TesterServiceException {
        try {
            log.info("start testing");
            List<Question> questions = questionsReader.loadQuestions();
            List<Answer> answers = new ArrayList<>();
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                userOutput.printQuestion(question, i);
                answers.add(answerReader.readAnswer(question));
             }
            userOutput.printResult(answers);
            return answers;
        } catch (QuestionsSourceException e) {
            throw new TesterServiceException(e);
        } catch (Exception e) {
            throw new TesterServiceException(e);
        } finally {
            answerReader.close();
        }
    }
}
