package ru.otus.levina.hw02.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestResult {
    private final Person person;
    private final List<Answer> answers;
    private final boolean passed;
}
