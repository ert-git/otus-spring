package ru.otus.levina.hw03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

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
