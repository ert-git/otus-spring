package ru.otus.levina.hw01.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Answer {
    private final Question question;
    private final String answer;

    public boolean isCorrect() {
        return question.isCorrectAnswer(answer);
    }
}
