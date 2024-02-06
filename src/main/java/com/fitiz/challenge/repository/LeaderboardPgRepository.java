package com.fitiz.challenge.repository;

import com.fitiz.challenge.provider.LeaderboardPgProvider;
import com.fitiz.challenge.tables.pojos.Leaderboard;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LeaderboardPgRepository {

    private final LeaderboardPgProvider leaderboardPgProvider;

    public LeaderboardPgRepository(LeaderboardPgProvider leaderboardPgProvider) {
        this.leaderboardPgProvider = leaderboardPgProvider;
    }

    public void addToLeaderboardPg(UUID challengeId, UUID userId) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setChallengeId(challengeId);
        leaderboard.setParticipantId(userId);
        leaderboardPgProvider.addToLeaderboard(leaderboard);
    }
}
