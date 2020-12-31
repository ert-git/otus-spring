package ru.otus.levina.hw03.services.core;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw03.config.AppProperties;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final Locale locale;

    public MessageServiceImpl(MessageSource messageSource, AppProperties props) {
        this.messageSource = messageSource;
        this.locale = props.getLocale();
    }

    @Override
    public String getMessage(String message, Object... objects) {
        return messageSource.getMessage(message, objects, locale);
    }
}