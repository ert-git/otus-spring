package ru.otus.levina.hw02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.levina.hw02.repository.CsvQuestionsRepository;
import ru.otus.levina.hw02.repository.QuestionsRepository;
import ru.otus.levina.hw02.services.core.PersonService;
import ru.otus.levina.hw02.services.core.PersonServiceImpl;
import ru.otus.levina.hw02.services.core.TesterService;
import ru.otus.levina.hw02.services.core.TesterServiceImpl;
import ru.otus.levina.hw02.services.formatters.MessageFormatter;
import ru.otus.levina.hw02.services.formatters.MessageFormatterImpl;
import ru.otus.levina.hw02.services.io.TesterServiceIO;
import ru.otus.levina.hw02.services.io.TesterServiceIOImpl;
import ru.otus.levina.hw02.services.io.UserIO;
import ru.otus.levina.hw02.services.io.UserIOImpl;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public TesterService testerService(QuestionsRepository questionsRepo,
                                       UserIO userIO,
                                       @Value("${questions.persent_to_pass}") float percentToPass) {
        return new TesterServiceImpl(questionsRepo, testerServiceIO(userIO, formatter()), percentToPass, personService(userIO));
    }

    @Bean
    public PersonService personService(UserIO userIO) {
        return new PersonServiceImpl(userIO);
    }

    @Bean
    public MessageFormatter formatter() {
        return new MessageFormatterImpl();
    }


    @Bean
    public UserIO userIO() {
        return new UserIOImpl(System.in, System.out);
    }

    @Bean
    public TesterServiceIO testerServiceIO(UserIO io, MessageFormatter formatter) {
        return new TesterServiceIOImpl(io, formatter);
    }

    @Bean
    public QuestionsRepository questionsRepo(@Value("${questions.path}") String csvResourceName) {
        return new CsvQuestionsRepository(csvResourceName);
    }

}
