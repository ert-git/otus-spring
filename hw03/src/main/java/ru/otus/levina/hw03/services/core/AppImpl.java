package ru.otus.levina.hw03.services.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.domain.Person;

@Slf4j
@Service
@AllArgsConstructor
public class AppImpl implements App {
    private final TesterService tester;
    private final PersonService personService;

    @Override
    public void start() throws TesterServiceException {
        Person person = personService.readPerson();
        tester.executeTest(person);
    }


}
