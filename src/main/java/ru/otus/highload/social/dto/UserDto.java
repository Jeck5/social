package ru.otus.highload.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.highload.social.model.Gender;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto { //TODO validation processing?

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
