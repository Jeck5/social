package ru.otus.highload.social.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    Long id;

    String login;

    String password;

    Role role;

    String firstName;

    String lastName;

    Integer age;

    Gender gender;

    String interests;

    String city;

}
