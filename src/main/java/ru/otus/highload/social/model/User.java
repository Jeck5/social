package ru.otus.highload.social.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {

    UUID id;

    String login;

    String password;

    Role role;

    String firstName;

    String lastName;

    int age;

    Gender gender;

    String interests;

    String city;

}
