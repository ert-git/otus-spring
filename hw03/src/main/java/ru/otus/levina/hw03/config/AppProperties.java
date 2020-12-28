package ru.otus.levina.hw03.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.Locale;


@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private int percentToPass;
    private Locale locale;
    private String csvResourceName;
}
