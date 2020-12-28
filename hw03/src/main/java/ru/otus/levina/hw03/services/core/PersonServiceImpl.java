package ru.otus.levina.hw03.services.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.common.Messages;
import ru.otus.levina.hw03.domain.Person;
import ru.otus.levina.hw03.services.io.UserIO;

import static ru.otus.levina.hw03.common.Messages.ERROR_EMPTY_INPUT;

@Slf4j
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final UserIO io;
    private final MessageService messageService;

    @Override
    public Person readPerson() {
        return new Person(
                readAnswer(messageService.getMessage(Messages.PERSON_FIRSTNAME_QUESTION)),
                readAnswer(messageService.getMessage(Messages.PERSON_LASTTNAME_QUESTION)));
    }

    public String readAnswer(String question) {
        io.print(question);
        String userinput = io.read();
        while (userinput.isEmpty()) {
            io.print(messageService.getMessage(ERROR_EMPTY_INPUT));
            io.print(question);
            userinput = io.read();
        }
        return userinput;
    }

}
