package ru.otus.levina.hw01.tests;

import org.junit.jupiter.api.Test;
import ru.otus.levina.hw01.core.TesterServiceImpl;
import ru.otus.levina.hw01.domain.Answer;
import ru.otus.levina.hw01.input.AnswerReader;
import ru.otus.levina.hw01.input.AnswerReaderImpl;
import ru.otus.levina.hw01.input.InputReader;
import ru.otus.levina.hw01.sources.CsvQuestionsSourceReader;
import ru.otus.levina.hw01.sources.QuestionsSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {


    @Test
    void checkCreationQuestionWhenOk() throws Exception {
        InputReader reader = new MockInputReader(new String[]{"7", "12", "c", "0", "green"});
        MockUserOutput out = new MockUserOutput();
        QuestionsSource src = new CsvQuestionsSourceReader("questions.csv");
        AnswerReader answerReader = new AnswerReaderImpl(reader, out);
        TesterServiceImpl impl = new TesterServiceImpl(src, answerReader, out);
        List<Answer> answers = impl.start();
        assertEquals(5, out.getQuestions().size());
        assertTrue(answers.get(0).isCorrect());
        assertTrue(answers.get(1).isCorrect());
        assertFalse(answers.get(2).isCorrect());
        assertFalse(answers.get(3).isCorrect());
        assertFalse(answers.get(4).isCorrect());
    }

}