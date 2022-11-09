package com.example.matchmaker.repository;

import com.example.matchmaker.entity.User;

import java.util.List;

public interface UserRepository {

    void save(User user);
    List<User> findAll();
    User deleteByName(String name);
}
