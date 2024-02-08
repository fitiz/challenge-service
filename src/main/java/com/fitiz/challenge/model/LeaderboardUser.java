package com.fitiz.challenge.model;

import java.io.Serializable;

public record LeaderboardUser(String username, Integer rank, Integer steps) implements Serializable {
}
