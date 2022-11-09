package com.example.matchmaker.controller;

import com.example.matchmaker.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping(path = "/users", consumes = "application/json")
public interface MatchmakerApi {

    @PostMapping
    void joinUser(@Valid @RequestBody UserDto dto);
}
