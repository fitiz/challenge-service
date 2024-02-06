package com.fitiz.challenge.model;

import java.util.UUID;

public record ParticipantProgress(UUID userId, String username, Integer points) {

  @Override
  public String toString() {
    return "ParticipantProgress{" +
            "userId=" + userId +
            ", username=" + username +
            ", points=" + points +
            '}';
  }
}
