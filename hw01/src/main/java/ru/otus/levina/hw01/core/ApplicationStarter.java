package ru.otus.levina.hw01.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class ApplicationStarter {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TesterServiceImpl tester = context.getBean(TesterServiceImpl.class);
        try {
            tester.start();
        } catch (Exception e) {
            log.error("start failed", e);
        }
    }
}
