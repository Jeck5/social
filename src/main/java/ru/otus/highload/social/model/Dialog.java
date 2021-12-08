package ru.otus.highload.social.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dialog {

    Long id;

    String name;

    Long creatorId;

    LocalDateTime creationTimestamp;

}
