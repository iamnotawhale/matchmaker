package com.example.matchmaker.service.impl;

import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.mapper.UserMapper;
import com.example.matchmaker.repository.UserRepository;
import com.example.matchmaker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public void join(UserDto dto) {
        repository.save(mapper.from(dto));
    }
}
