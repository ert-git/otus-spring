package ru.otus.levina.hw02.tests;

import org.junit.jupiter.api.Test;
import ru.otus.levina.hw02.domain.Person;
import ru.otus.levina.hw02.domain.TestResult;
import ru.otus.levina.hw02.repository.CsvQuestionsRepository;
import ru.otus.levina.hw02.repository.QuestionsRepository;
import ru.otus.levina.hw02.services.core.PersonService;
import ru.otus.levina.hw02.services.core.PersonServiceImpl;
import ru.otus.levina.hw02.services.core.TesterServiceImpl;
import ru.otus.levina.hw02.services.formatters.MessageFormatterImpl;
import ru.otus.levina.hw02.services.io.TesterServiceIO;
import ru.otus.levina.hw02.services.io.TesterServiceIOImpl;
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
    void testTestPassed() throws Exception {
        Person testPerson = new Person("testfn", "testln");
        byte[] bytes = String.join("\n", new String[]{
                testPerson.getFirstName(), testPerson.getLastName(),
                "7", "12", "c", "0", "green"}).getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        QuestionsRepository qrepo = new CsvQuestionsRepository("questions.csv");
        UserIO io = new UserIOImpl(in, System.out);
        TesterServiceIO tsio = new TesterServiceIOImpl(io, new MessageFormatterImpl());
        PersonService personService = new PersonServiceImpl(io);
        TesterServiceImpl testerService = new TesterServiceImpl(qrepo, tsio, 40, personService);
        TestResult result = testerService.executeTest();
        assertTrue(result.isPassed());
    }

    @Test
    void testTestFailed() throws Exception {
        Person testPerson = new Person("testfn", "testln");
        byte[] bytes = String.join("\n", new String[]{
                testPerson.getFirstName(), testPerson.getLastName(),
                "7", "12", "c", "0", "green"}).getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        QuestionsRepository qrepo = new CsvQuestionsRepository("questions.csv");
        UserIO io = new UserIOImpl(in, System.out);
        TesterServiceIO tsio = new TesterServiceIOImpl(io, new MessageFormatterImpl());
        PersonService personService = new PersonServiceImpl(io);
        TesterServiceImpl testerService = new TesterServiceImpl(qrepo, tsio, 80, personService);
        TestResult result = testerService.executeTest();
        assertFalse(result.isPassed());
    }

}