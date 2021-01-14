package ru.otus.levina.hw05.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private long id;
    private String firstName;
    private String lastName;
    private String middleName;

    public Author(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getAuthorFullName() {

        return (Optional.ofNullable(getLastName()).orElse("")
                + " " + Optional.ofNullable(getFirstName()).orElse("")
                + " " + Optional.ofNullable(getMiddleName()).orElse(""))
                .trim();
    }

}
