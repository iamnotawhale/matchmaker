package com.example.matchmaker.mapper.impl;

import com.example.matchmaker.dto.UserDto;
import com.example.matchmaker.entity.User;
import com.example.matchmaker.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User from(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .skill(dto.getSkill())
                .latency(dto.getLatency())
                .build();
    }

    @Override
    public UserDto from(User user) {
        return UserDto.builder()
                .name(user.getName())
                .skill(user.getSkill())
                .latency(user.getLatency())
                .build();
    }
}
