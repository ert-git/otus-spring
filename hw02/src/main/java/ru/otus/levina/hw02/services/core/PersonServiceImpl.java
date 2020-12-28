package ru.otus.levina.hw02.services.core;

import lombok.extern.slf4j.Slf4j;
import ru.otus.levina.hw02.common.Messages;
import ru.otus.levina.hw02.domain.Person;
import ru.otus.levina.hw02.services.io.UserIO;

import static ru.otus.levina.hw02.common.Messages.ERROR_EMPTY_INPUT;

@Slf4j
public class PersonServiceImpl implements PersonService {

    private final UserIO io;

    public PersonServiceImpl(UserIO io) {
        this.io = io;
    }

    @Override
    public Person readPerson() {
        return new Person(
                readAnswer(Messages.PERSON_FIRSTNAME_QUESTION),
                readAnswer(Messages.PERSON_LASTTNAME_QUESTION));
    }

    public String readAnswer(String question) {
        io.print(question);
        String userinput = io.read();
        while (userinput.isEmpty()) {
            io.print(ERROR_EMPTY_INPUT);
            io.print(question);
            userinput = io.read();
        }
        return userinput;
    }

}
