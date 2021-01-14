package ru.otus.levina.hw03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.levina.hw03.config.AppProperties;
import ru.otus.levina.hw03.services.core.App;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
public class ApplicationStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationStarter.class, args);
        App app = context.getBean(App.class);
        try {
            app.start();
        } catch (Exception e) {
            log.error("start failed", e);
        }
    }
}
