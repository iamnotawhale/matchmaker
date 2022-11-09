package com.example.matchmaker.controller.impl;

import com.example.matchmaker.controller.MatchmakerApi;
import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchmakerController implements MatchmakerApi {

    private final UserService service;

    @Override
    public void joinUser(UserDto dto) {
        service.join(dto);
    }
}
