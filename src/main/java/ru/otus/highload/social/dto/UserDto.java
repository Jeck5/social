package ru.otus.highload.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.otus.highload.social.model.Gender;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    Long id;

    @Size(min = 3, max = 40)
    String login;

    @Size(min = 3)
    String password;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    int age;

    Gender gender;

    String interests;

    @NotEmpty
    String city;
}
