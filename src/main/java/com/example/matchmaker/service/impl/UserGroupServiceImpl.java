package com.example.matchmaker.service.impl;

import com.example.matchmaker.entity.User;
import com.example.matchmaker.entity.UserGroup;
import com.example.matchmaker.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {

    private static final AtomicInteger groupId = new AtomicInteger(1);

    @Override
    public void createGroup(List<User> users) {
        LocalDateTime now = LocalDateTime.now();
        logInfo(UserGroup.builder()
                .groupId(groupId.getAndIncrement())
                .userNames(getUserNames(users))
                .minSkill(getMinSkill(users))
                .maxSkill(getMaxSkill(users))
                .avgSkill(getAvgSkill(users))
                .minLatency(getMinLatency(users))
                .maxLatency(getMaxLatency(users))
                .avgLatency(getAvgLatency(users))
                .minTimeSpent(getMinTimeSpent(users, now))
                .maxTimeSpent(getMaxTimeSpent(users, now))
                .avgTimeSpent(getAvgTimeSpent(users, now))
                .build());
    }

    private double getMinSkill(List<User> users) {
        return users.stream()
                .mapToDouble(User::getSkill)
                .min()
                .orElse(0.0);
    }

    private double getMaxSkill(List<User> users) {
        return users.stream()
                .mapToDouble(User::getSkill)
                .max()
                .orElse(0.0);
    }

    private double getAvgSkill(List<User> users) {
        return Math.floor(users.stream()
                .mapToDouble(User::getSkill)
                .average()
                .orElse(0.0));
    }

    private double getMinLatency(List<User> users) {
        return users.stream()
                .mapToDouble(User::getLatency)
                .min()
                .orElse(0.0);
    }

    private double getMaxLatency(List<User> users) {
        return users.stream()
                .mapToDouble(User::getLatency)
                .max()
                .orElse(0.0);
    }

    private double getAvgLatency(List<User> users) {
        return Math.floor(users.stream()
                .mapToDouble(User::getLatency)
                .average()
                .orElse(0.0));
    }

    private int getMinTimeSpent(List<User> users, LocalDateTime now) {
        return users.stream()
                .mapToInt(user -> (int) (Duration.between(user.getJoinAt(), now)).getSeconds())
                .min()
                .orElse(0);
    }

    private int getMaxTimeSpent(List<User> users, LocalDateTime now) {
        return users.stream()
                .mapToInt(user -> (int) (Duration.between(user.getJoinAt(), now)).getSeconds())
                .max()
                .orElse(0);
    }

    private double getAvgTimeSpent(List<User> users, LocalDateTime now) {
        return Math.round(users.stream()
                .mapToDouble(user -> (Duration.between(user.getJoinAt(), now)).getSeconds())
                .average()
                .orElse(0));
    }

    private List<String> getUserNames(List<User> users) {
        return users.stream()
                .map(User::getName)
                .toList();
    }

    private void logInfo(UserGroup userGroup) {
        log.info(userGroup.toString());
    }
}
