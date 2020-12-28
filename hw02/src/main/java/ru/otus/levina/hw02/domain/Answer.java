package ru.otus.levina.hw02.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Answer {

    private final Question question;
    private final String answer;

    public boolean isCorrect() {
        if (question.isOpen()) {
            return answer == null || answer.isEmpty();
        }
        return question.getCorrectAnswer().equalsIgnoreCase(answer);
    }
}
