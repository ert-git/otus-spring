package ru.otus.levina.hw02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.levina.hw02.services.core.TesterService;

@Slf4j
@Configuration
@ComponentScan(basePackages = "ru.otus.levina.hw02.config")
public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationStarter.class);
        TesterService tester = (TesterService) context.getBean("testerService");
        try {
            tester.executeTest();
        } catch (Exception e) {
            log.error("start failed", e);
        }
    }
}
