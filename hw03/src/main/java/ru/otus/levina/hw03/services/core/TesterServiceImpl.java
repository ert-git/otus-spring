package ru.otus.levina.hw03.services.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.config.AppProperties;
import ru.otus.levina.hw03.domain.Answer;
import ru.otus.levina.hw03.domain.Person;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;
import ru.otus.levina.hw03.repository.QuestionsRepository;
import ru.otus.levina.hw03.repository.QuestionsRepositoryException;
import ru.otus.levina.hw03.services.io.TesterServiceIO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TesterServiceImpl implements TesterService {
    private final QuestionsRepository questionsRepo;
    private final TesterServiceIO io;
    private final float percentToPass;

    public TesterServiceImpl(QuestionsRepository questionsReader,
                             TesterServiceIO io,
                             AppProperties props) {
        this.questionsRepo = questionsReader;
        this.io = io;
        this.percentToPass = props.getPercentToPass();
    }

    @Override
    public TestResult executeTest(Person person) throws TesterServiceException {
        log.info("start testing: percentToPass={}", percentToPass);
        try {
            List<Answer> answers = readAnswers();
            TestResult result = new TestResult(person, answers, isPassed(answers));
            io.printResult(result);
            return result;
        } catch (Exception e) {
            throw new TesterServiceException(e);
        } finally {
            log.info("stop testing");
        }
    }

    private boolean isPassed(List<Answer> answers) {
        return
                answers.size() > 0 && 100 * answers.stream().filter(a -> a.isCorrect()).count() / answers.size() >= percentToPass;
    }

    private List<Answer> readAnswers() throws QuestionsRepositoryException {
        List<Question> questions = questionsRepo.loadQuestions();
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            io.printQuestion(question);
            answers.add(io.readAnswer(question));
        }
        return answers;
    }

}
