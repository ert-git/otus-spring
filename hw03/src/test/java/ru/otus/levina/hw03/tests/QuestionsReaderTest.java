package ru.otus.levina.hw03.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.levina.hw03.config.AppProperties;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.repository.CsvQuestionsRepository;
import ru.otus.levina.hw03.repository.QuestionsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class QuestionsReaderTest {

    private final static int NUMBER_OF_QUESTIONS = 5;
    private final static int NUMBER_OF_CHOICES_Q1 = 3;
    private final static int NUMBER_OF_CHOICES_Q2 = 3;
    private final static int NUMBER_OF_CHOICES_Q3 = 3;
    private final static int NUMBER_OF_CHOICES_Q4 = 0;
    private final static int NUMBER_OF_CHOICES_Q5 = 1;

    @Autowired
    private CsvQuestionsRepository qrepo;

    @Test
    void testChoicesSizeInQuestionsWithChoices() throws Exception {
        List<Question> questions = qrepo.loadQuestions();
        assertEquals(NUMBER_OF_QUESTIONS, questions.size());
        assertEquals(NUMBER_OF_CHOICES_Q1, questions.get(0).getChoices().size());
        assertEquals(NUMBER_OF_CHOICES_Q2, questions.get(1).getChoices().size());
        assertEquals(NUMBER_OF_CHOICES_Q3, questions.get(2).getChoices().size());
        assertEquals(NUMBER_OF_CHOICES_Q4, questions.get(3).getChoices().size());
        assertEquals(NUMBER_OF_CHOICES_Q5, questions.get(4).getChoices().size());
    }

}