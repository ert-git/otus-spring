package ru.otus.levina.hw03.services.io;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.services.core.MessageService;

@Service
@AllArgsConstructor
public class UserIOFacadeImpl implements UserIOFacade {
    private final UserIO io;
    private final MessageService messageService;

    @Override
    public String readNotEmptyString(String prompt, String errorInputMessage) {
        io.print(prompt);
        String userinput = io.read();
        while (userinput.isEmpty()) {
            io.print(messageService.getMessage(errorInputMessage));
            io.print(prompt);
            userinput = io.read();
        }
        return userinput;
    }

    @Override
    public void print(String msg) {
        io.print(msg);
    }

}
