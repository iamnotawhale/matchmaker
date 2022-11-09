package com.example.matchmaker.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Setter(AccessLevel.NONE)
public class User {

    String name;

    Double skill;

    Double latency;

    @Builder.Default
    LocalDateTime joinAt = LocalDateTime.now();

    @Setter
    int matchmakingRating;
}
