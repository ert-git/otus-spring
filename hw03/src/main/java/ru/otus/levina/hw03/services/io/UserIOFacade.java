package ru.otus.levina.hw03.services.io;

import ru.otus.levina.hw03.domain.Answer;
import ru.otus.levina.hw03.domain.Question;
import ru.otus.levina.hw03.domain.TestResult;

public interface UserIOFacade {
     String readNotEmptyString(String prompt, String errorInputMessage);

     void print(String msg);
}
