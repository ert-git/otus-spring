package ru.otus.levina.hw02.tests;

import org.junit.jupiter.api.Test;
import ru.otus.levina.hw02.domain.Person;
import ru.otus.levina.hw02.domain.TestResult;
import ru.otus.levina.hw02.repository.CsvQuestionsRepository;
import ru.otus.levina.hw02.repository.QuestionsRepository;
import ru.otus.levina.hw02.services.core.TesterServiceImpl;
import ru.otus.levina.hw02.services.formatters.ConsoleMessageFormatter;
import ru.otus.levina.hw02.services.io.UserIO;
import ru.otus.levina.hw02.services.io.UserIOImpl;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TesterServiceTest {

    /**
     * Я обязательно освою Mockito, но сейчас на это совсем нет времени :(
     */

    @Test
    void _test_passed() throws Exception {
        Person testPerson = new Person("testfn", "testln");
        byte[] bytes = String.join("\n", new String[]{
                testPerson.getFirstName(), testPerson.getLastName(),
                "7", "12", "c", "0", "green"}).getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        QuestionsRepository qrepo = new CsvQuestionsRepository("questions.csv");
        UserIO io = new UserIOImpl(in, System.out, new ConsoleMessageFormatter());
        TesterServiceImpl testerService = new TesterServiceImpl(qrepo, io, 40);
        TestResult result = testerService.executeTest();
        assertTrue(result.isPassed());
    }

    @Test
    void _test_failed() throws Exception {
        Person testPerson = new Person("testfn", "testln");
        byte[] bytes = String.join("\n", new String[]{
                testPerson.getFirstName(), testPerson.getLastName(),
                "7", "12", "c", "0", "green"}).getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        QuestionsRepository qrepo = new CsvQuestionsRepository("questions.csv");
        UserIO io = new UserIOImpl(in, System.out, new ConsoleMessageFormatter());
        TesterServiceImpl testerService = new TesterServiceImpl(qrepo, io, 80);
        TestResult result = testerService.executeTest();
        assertFalse(result.isPassed());
    }

}