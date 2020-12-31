package ru.otus.levina.hw03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Question {
    @NonNull
    private final int id;
    @NonNull
    private final String question;

    private String correctAnswer;

    public boolean isOpen() {
        return correctAnswer == null || correctAnswer.isEmpty();
    }

    private final Set<String> choices = new HashSet<>();

}
