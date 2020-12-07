package ru.otus.levina.hw02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.levina.hw02.repository.CsvQuestionsRepository;
import ru.otus.levina.hw02.repository.QuestionsRepository;
import ru.otus.levina.hw02.services.core.TesterService;
import ru.otus.levina.hw02.services.core.TesterServiceImpl;
import ru.otus.levina.hw02.services.io.UserIO;
import ru.otus.levina.hw02.services.io.UserIOImpl;
import ru.otus.levina.hw02.services.formatters.ConsoleMessageFormatter;
import ru.otus.levina.hw02.services.formatters.MessageFormatter;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Bean
    public TesterService testerService(QuestionsRepository questionsRepo,
                                       UserIO userIO,
                                       @Value("${questions.persent_to_pass}") float percentToPass) {
        return new TesterServiceImpl(questionsRepo, userIO, percentToPass);
    }

    @Bean
    public MessageFormatter formatter() {
        return new ConsoleMessageFormatter();
    }


    @Bean
    public UserIO userIO(MessageFormatter formatter) {
        return new UserIOImpl(System.in, System.out, formatter);
    }

    @Bean
    public QuestionsRepository questionsRepo(@Value("${questions.path}") String csvResourceName) {
        return new CsvQuestionsRepository(csvResourceName);
    }

}
