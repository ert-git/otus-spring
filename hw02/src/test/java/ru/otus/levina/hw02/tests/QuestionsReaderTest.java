package ru.otus.levina.hw02.tests;

import org.junit.jupiter.api.Test;
import ru.otus.levina.hw02.domain.Person;
import ru.otus.levina.hw02.domain.Question;
import ru.otus.levina.hw02.domain.TestResult;
import ru.otus.levina.hw02.repository.CsvQuestionsRepository;
import ru.otus.levina.hw02.repository.QuestionsRepository;
import ru.otus.levina.hw02.services.core.TesterServiceImpl;
import ru.otus.levina.hw02.services.formatters.ConsoleMessageFormatter;
import ru.otus.levina.hw02.services.io.UserIO;
import ru.otus.levina.hw02.services.io.UserIOImpl;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionsReaderTest {

    @Test
    void _test_choices() throws Exception {
        QuestionsRepository qrepo = new CsvQuestionsRepository("questions.csv");
        List<Question> questions = qrepo.loadQuestions();
        assertEquals(5, questions.size());
        assertEquals(3, questions.get(0).getChoices().size());
        assertEquals(3, questions.get(1).getChoices().size());
        assertEquals(3, questions.get(2).getChoices().size());
        assertEquals(0, questions.get(3).getChoices().size());
        assertEquals(1, questions.get(4).getChoices().size());
    }

}