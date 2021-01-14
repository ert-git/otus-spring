package ru.otus.levina.hw03.services.core;

import ru.otus.levina.hw03.domain.Person;
import ru.otus.levina.hw03.domain.TestResult;

public interface TesterService {
    TestResult executeTest(Person person) throws TesterServiceException;
}
