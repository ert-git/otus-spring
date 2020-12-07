package ru.otus.levina.hw02.services.core;

import lombok.extern.slf4j.Slf4j;
import ru.otus.levina.hw02.common.Messages;
import ru.otus.levina.hw02.domain.Answer;
import ru.otus.levina.hw02.domain.Person;
import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;
import ru.otus.levina.hw02.services.io.UserIO;
import ru.otus.levina.hw02.repository.QuestionsRepository;
import ru.otus.levina.hw02.repository.QuestionsRepositoryException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TesterServiceImpl implements TesterService {

    private final QuestionsRepository questionsRepo;
    private final UserIO io;
    private final float percentToPass;

    public TesterServiceImpl(QuestionsRepository questionsReader, UserIO io, float percentToPass) {
        this.questionsRepo = questionsReader;
        this.io = io;
        this.percentToPass = percentToPass;
    }

    @Override
    public TestResult executeTest() throws TesterServiceException {
        log.info("start testing");
        try {
            Person person = readPerson();
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

    private boolean isPassed( List<Answer> answers) {
        return
                100 * answers.stream().filter(a -> a.isCorrect()).count() / answers.size() >= percentToPass;
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

    private Person readPerson() {
        Question fnq =  new Question(0, Messages.PERSON_FIRSTNAME_QUESTION);
        io.printQuestion(fnq);
        Answer fna = io.readAnswer(fnq);
        Question lnq =  new Question(0, Messages.PERSON_LASTTNAME_QUESTION);
        io.printQuestion(lnq);
        Answer lna = io.readAnswer(lnq);
        return new Person(fna.getAnswer(),lna.getAnswer());
    }
}
