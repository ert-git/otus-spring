package ru.otus.levina.hw02.services.core;

import ru.otus.levina.hw02.domain.TestResult;

public interface TesterService {
    TestResult executeTest() throws TesterServiceException;
}
