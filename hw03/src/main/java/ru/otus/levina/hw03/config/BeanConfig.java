package ru.otus.levina.hw03.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.levina.hw03.repository.CsvQuestionsRepository;
import ru.otus.levina.hw03.repository.QuestionsRepository;
import ru.otus.levina.hw03.services.core.*;
import ru.otus.levina.hw03.services.formatters.MessageFormatter;
import ru.otus.levina.hw03.services.formatters.MessageFormatterImpl;
import ru.otus.levina.hw03.services.io.TesterServiceIO;
import ru.otus.levina.hw03.services.io.TesterServiceIOImpl;
import ru.otus.levina.hw03.services.io.UserIO;
import ru.otus.levina.hw03.services.io.UserIOImpl;

@Configuration
public class BeanConfig {

    @Bean
    public UserIO userIO() {
        return new UserIOImpl(System.in, System.out);
    }

}
