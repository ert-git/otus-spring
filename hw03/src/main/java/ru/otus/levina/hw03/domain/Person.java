package ru.otus.levina.hw03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private final String firstName;
    private final String lastName;
}
