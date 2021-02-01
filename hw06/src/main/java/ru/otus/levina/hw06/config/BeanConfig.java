package ru.otus.levina.hw06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.levina.hw06.services.UserIO;
import ru.otus.levina.hw06.services.UserIOImpl;

@Configuration
public class BeanConfig {

    @Bean
    public UserIO userIO() {
        return new UserIOImpl(System.in, System.out);
    }

}
