package com.example.matchmaker.service.impl;

import com.example.matchmaker.entity.User;
import com.example.matchmaker.repository.UserRepository;
import com.example.matchmaker.service.MatchmakerService;
import com.example.matchmaker.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchmakerServiceImpl implements MatchmakerService {

    private static final int MAX_LATENCY = 20; // can be specified in application.properties
    private final UserRepository repository;
    private final UserGroupService userGroupService;
    @Value("${group.size}")
    private int groupSize;

    @Override
    public void startMatchmaking() {
        List<User> users = repository.findAll();
        if (!users.isEmpty()) {
            // Find something who has excessive latency at matchmaking queue
            Optional<User> excessiveLatencyUsers = findExcessiveLatencyUsers(users);
            if (excessiveLatencyUsers.isEmpty()) {
                // Create group by matchmaking rating
                createUsersGroup(selectByRating(users));
            } else {
                // Create group by excessive latency player
                createUsersGroup(selectGroup(users, excessiveLatencyUsers.get()));
            }
        } else {
            log.debug("Waiting players connection..");
        }
    }

    private Optional<User> findExcessiveLatencyUsers(List<User> users) {
        // Find any player whose waiting time is longer than the maximum latency
        return users.stream()
                .filter(user -> Duration.between(user.getJoinAt(), LocalDateTime.now()).toSeconds() > MAX_LATENCY)
                .findAny();
    }

    private void createUsersGroup(List<User> users) {
        // Create best matches players group
        if (!users.isEmpty()) {
            List<User> usersToGroup = new ArrayList<>();
            users.forEach(user -> usersToGroup.add(repository.deleteByName(user.getName())));
            userGroupService.createGroup(usersToGroup);
        }
    }

    private List<User> selectByRating(List<User> users) {
        // groupSize * multiplier needed for a large
        // selection and more accurate data for grouping
        if (users.size() >= groupSize * 3) {
            // Best matched groups pool
            Map<Integer, List<User>> bestMatchedPlayersGroups = new HashMap<>();

            // Best matched players for each
            // Filling the map based on the sum of matchmaking rating
            users.forEach(user -> {
                List<User> bestMatchedPlayers = selectGroup(users, user);

                bestMatchedPlayersGroups.putIfAbsent(bestMatchedPlayers.stream()
                        .mapToInt(User::getMatchmakingRating)
                        .sum(), bestMatchedPlayers);
            });
            // Return one best group of players based
            // on minimum of matchmakers rating sum
            return bestMatchedPlayersGroups.get(Collections.min(bestMatchedPlayersGroups.keySet()));
        } else {
            log.debug("Players pool is waiting more players");
            return Collections.emptyList();
        }
    }

    private List<User> selectGroup(List<User> users, User user) {
        // Set matchmaking rating to each player in users list based this user
        users.forEach(u -> u.setMatchmakingRating(findMatchmakingRating(u, user.getSkill(), user.getLatency())));
        // Return best matches players with groupSize limit
        return users.stream()
                .sorted(Comparator.comparing(User::getMatchmakingRating).thenComparing(User::getJoinAt))
                .limit(groupSize)
                .collect(Collectors.toList());
    }

    private int findMatchmakingRating(User user, Double skill, Double latency) {
        // Generation of a matchmaking rating based on the closest
        // latency and skill indicators relative to a given user
        return (int) (Math.ceil(Math.abs(user.getSkill() - skill))
                + Math.ceil(Math.abs(user.getLatency() - latency)));
    }
}
