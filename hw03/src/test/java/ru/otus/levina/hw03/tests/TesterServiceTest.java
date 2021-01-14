package ru.otus.levina.hw03.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.levina.hw03.domain.Answer;
import ru.otus.levina.hw03.domain.Person;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;
import ru.otus.levina.hw03.repository.QuestionsRepository;
import ru.otus.levina.hw03.services.core.TesterService;
import ru.otus.levina.hw03.services.io.TesterServiceIO;
import ru.otus.levina.hw03.services.io.UserIO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@SpringBootTest
@TestPropertySource(properties = "app.percentToPass=60")
class TesterServiceTest {
    private static final Person testPerson = new Person("testfn", "testln");

    @MockBean
    private QuestionsRepository qrepo;
    @MockBean
    private TesterServiceIO tsio;
    @Autowired
    private TesterService testerService;

    private static List<Question> questions = Arrays.asList(new Question[]{
            new Question(1, "test q1", "1"),
            new Question(2, "test q2", "2"),
            new Question(3, "test q3", "3"),
            new Question(4, "test q4", "4")
    });


    @Test
    void testTestPassed() throws Exception {
        given(qrepo.getQuestions()).willReturn(questions);
        given(tsio.getAnswer(questions.get(0))).willReturn(new Answer(questions.get(0), "1"));
        given(tsio.getAnswer(questions.get(1))).willReturn(new Answer(questions.get(1), "2"));
        given(tsio.getAnswer(questions.get(2))).willReturn(new Answer(questions.get(2), "3"));
        given(tsio.getAnswer(questions.get(3))).willReturn(new Answer(questions.get(3), "wrong"));
        TestResult result = testerService.executeTest(testPerson);
        assertTrue(result.isPassed());
    }

    @Test
    void testTestFailed() throws Exception {
        given(qrepo.getQuestions()).willReturn(questions);
        given(tsio.getAnswer(questions.get(0))).willReturn(new Answer(questions.get(0), "1"));
        given(tsio.getAnswer(questions.get(1))).willReturn(new Answer(questions.get(1), "2"));
        given(tsio.getAnswer(questions.get(2))).willReturn(new Answer(questions.get(2), "wrong"));
        given(tsio.getAnswer(questions.get(3))).willReturn(new Answer(questions.get(3), "wrong"));
        TestResult result = testerService.executeTest(testPerson);
        assertFalse(result.isPassed());
    }

}