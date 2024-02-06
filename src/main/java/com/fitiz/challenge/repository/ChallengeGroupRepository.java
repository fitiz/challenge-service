package com.fitiz.challenge.repository;

import com.fitiz.challenge.provider.ChallengeGroupProvider;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChallengeGroupRepository {

    private final ChallengeGroupProvider challengeGroupProvider;

    public ChallengeGroupRepository(ChallengeGroupProvider challengeGroupProvider) {
        this.challengeGroupProvider = challengeGroupProvider;
    }

    public Boolean addParticipantToChallengeSet(UUID challengeId, String username) {
        return challengeGroupProvider.addToChallengeSet(challengeId, username);
    }

    public Boolean removeParticipantFromChallengeSet(UUID challengeId, String username) {
        return challengeGroupProvider.removeFromChallengeSet(challengeId, username);
    }

    public String getChallengeId(UUID challengeId) {
        String groupId = challengeGroupProvider.getUnusedGroupId(challengeId);
        if (groupId == null) {
            groupId = buildChallengeLeaderboardId(challengeId);
        }
        return groupId;
    }

    public Boolean saveBackGroupId(UUID challengeId, String groupId) {
        return challengeGroupProvider.saveUnusedGroupId(challengeId, groupId);
    }

    private String buildChallengeLeaderboardId(UUID challengeId) {
        return "challenge-" + challengeId;
    }
}
