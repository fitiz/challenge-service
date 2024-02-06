package com.fitiz.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParticipantInfo {
    private UUID userId;
    private String username;
    @JsonIgnore
    private UUID challengeId;
    private Integer locationId;
    private Integer points;

    @JsonIgnore
    public boolean isInAnotherChallenge(UUID challengeId) {
        return this.challengeId != null && !this.challengeId.equals(challengeId);
    }

    @JsonIgnore
    public ParticipantProgress toParticipantProgress() {
        return new ParticipantProgress(this.userId, this.username, this.points);
    }

    @Override
    public String toString() {
        return "ParticipantInfo{" +
                "username='" + username + '\'' +
                ", locationId=" + locationId +
                ", challengeId=" + challengeId +
                '}';
    }
}
