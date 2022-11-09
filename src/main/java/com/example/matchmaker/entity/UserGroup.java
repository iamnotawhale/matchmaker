package com.example.matchmaker.entity;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class UserGroup {

    long groupId;
    @Builder.Default
    List<String> userNames = new ArrayList<>();
    double minSkill;
    double maxSkill;
    double avgSkill;
    double minLatency;
    double maxLatency;
    double avgLatency;
    int minTimeSpent;
    int maxTimeSpent;
    double avgTimeSpent;

    @Override
    public String toString() {
        return "Group " + groupId + ": " +
                "skill [min: " + minSkill +
                ", max: " + maxSkill +
                ", avg: " + avgSkill + "], " +
                "latency [min: " + minLatency +
                ", max: " + maxLatency +
                ", avg: " + avgLatency + "], " +
                "time spent [min: " + minTimeSpent +
                ", max: " + maxTimeSpent +
                ", avg: " + avgTimeSpent + "], " +
                " players [" + String.join(", ", userNames) + "]";
    }
}
