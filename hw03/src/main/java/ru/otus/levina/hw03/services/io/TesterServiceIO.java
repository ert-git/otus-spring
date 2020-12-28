package ru.otus.levina.hw03.services.io;

import ru.otus.levina.hw03.domain.Answer;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;

public interface TesterServiceIO {
     void printQuestion(Question q);
     Answer readAnswer(Question question) ;
     void printResult(TestResult result);
}
