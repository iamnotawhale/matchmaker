package com.example.matchmaker.config;

import com.example.matchmaker.service.MatchmakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Configuration {

    private final MatchmakerService service;

    @Scheduled(fixedDelay = 1000)
    public void startToCreateGroups() {
        service.startMatchmaking();
    }
}
