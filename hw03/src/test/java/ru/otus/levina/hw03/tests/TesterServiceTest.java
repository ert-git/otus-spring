package ru.otus.levina.hw03.tests;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.levina.hw03.config.AppProperties;
import ru.otus.levina.hw03.domain.Person;
import ru.otus.levina.hw03.domain.TestResult;
import ru.otus.levina.hw03.repository.CsvQuestionsRepository;
import ru.otus.levina.hw03.repository.QuestionsRepository;
import ru.otus.levina.hw03.services.core.TesterService;
import ru.otus.levina.hw03.services.core.TesterServiceImpl;
import ru.otus.levina.hw03.services.formatters.MessageFormatter;
import ru.otus.levina.hw03.services.io.TesterServiceIO;
import ru.otus.levina.hw03.services.io.TesterServiceIOImpl;
import ru.otus.levina.hw03.services.io.UserIO;
import ru.otus.levina.hw03.services.io.UserIOImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class TesterServiceTest {
    private static final Person testPerson = new Person("testfn", "testln");
    @Autowired
    private MessageFormatter formatter;
    @Mock
    private AppProperties appProps;

    private TesterService configureTesterService(int percent) throws Exception {
        given(appProps.getPercentToPass()).willReturn(percent);
        given(appProps.getCsvResourceName()).willReturn("questions");
        given(appProps.getLocale()).willReturn(Locale.US);

        byte[] bytes = String.join("\n", new String[]{
                "7", "12", "c", "0", "green"}).getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        QuestionsRepository qrepo = new CsvQuestionsRepository(appProps);
        UserIO io = new UserIOImpl(in, System.out);
        TesterServiceIO tsio = new TesterServiceIOImpl(io, formatter);
        return new TesterServiceImpl(qrepo, tsio, appProps);
    }

    @Test
    void testTestPassed() throws Exception {
        TesterService testerService = configureTesterService(40);
        TestResult result = testerService.executeTest(testPerson);
        assertTrue(result.isPassed());
    }

    @Test
    void testTestFailed() throws Exception {
        TesterService testerService = configureTesterService(80);
        TestResult result = testerService.executeTest(testPerson);
        assertFalse(result.isPassed());
    }

}