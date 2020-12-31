package ru.otus.levina.hw03.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.repository.CsvQuestionsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionsReaderTest {

    @Autowired
    private CsvQuestionsRepository qrepo;

    @Test
    void testChoicesSizeInQuestionsWithChoices() throws Exception {
        List<Question> questions = qrepo.getQuestions();
        int numberofQuestions = 5;
        assertEquals(numberofQuestions, questions.size());
        int[] correctAnswers = new int[]{3, 3, 3, 0, 2};
        for (int i = 0; i < correctAnswers.length; i++) {
            assertEquals( correctAnswers[i], questions.get(i).getChoices().size(), "Q: " + questions.get(i));
        }

    }

}