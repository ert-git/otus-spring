package ru.otus.levina.hw03.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.repository.CsvQuestionsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class QuestionsReaderTest {

    @Autowired
    private CsvQuestionsRepository qrepo;

    @Test
    void testChoicesSizeInQuestionsWithChoices() throws Exception {
        List<Question> questions = qrepo.getQuestions();
        int numberOfQuestions = 4;
        assertEquals(numberOfQuestions, questions.size());
        int[] correctAnswers = new int[]{3, 3, 3, 0};
        for (int i = 0; i < correctAnswers.length; i++) {
            assertEquals( correctAnswers[i], questions.get(i).getChoices().size(), "Q: " + questions.get(i));
        }
    }

}