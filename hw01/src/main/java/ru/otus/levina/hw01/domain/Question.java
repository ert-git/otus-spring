package ru.otus.levina.hw01.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Question {
    @NonNull
    private int id;
    @NonNull
    private String question;
    @NonNull
    private String correctAnswer;
    private final Set<String> answers = new HashSet<>();

    public boolean isCorrectAnswer(String answer) {
        return correctAnswer.equals(answer);
    }
}
