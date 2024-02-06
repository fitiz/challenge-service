package com.fitiz.challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChallengeInfo {
    private UUID id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;
    private Integer locationId;

    @JsonIgnore
    public boolean isActive() {
        return Instant.now().isAfter(this.startDate.toInstant()) && Instant.now().isBefore(this.finishDate.toInstant());
    }

    @Override
    public String toString() {
        return "ChallengeInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", locationId=" + locationId +
                '}';
    }
}
