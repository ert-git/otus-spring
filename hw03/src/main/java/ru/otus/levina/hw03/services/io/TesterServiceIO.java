package ru.otus.levina.hw03.services.io;

import ru.otus.levina.hw03.domain.Answer;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;

public interface TesterServiceIO {
     Answer getAnswer(Question question) ;
     void printResult(TestResult result);
}
