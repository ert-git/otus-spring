package ru.otus.levina.hw03.services.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.common.Messages;
import ru.otus.levina.hw03.domain.Person;
import ru.otus.levina.hw03.services.io.UserIO;
import ru.otus.levina.hw03.services.io.UserIOFacade;

import static ru.otus.levina.hw03.common.Messages.ERROR_EMPTY_INPUT;

@Slf4j
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final UserIOFacade io;

    @Override
    public Person readPerson() {
        return new Person(
                io.readNotEmptyString(Messages.PERSON_FIRSTNAME_QUESTION, ERROR_EMPTY_INPUT),
                io.readNotEmptyString(Messages.PERSON_LASTTNAME_QUESTION, ERROR_EMPTY_INPUT));
    }



}
