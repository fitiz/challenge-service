package com.fitiz.challenge.repository;

import com.fitiz.challenge.exception.ParticipantNotFoundException;
import com.fitiz.challenge.model.ParticipantInfo;
import com.fitiz.challenge.provider.ParticipantProvider;
import com.fitiz.challenge.tables.pojos.Participant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantRepository {

    private final ParticipantProvider participantProvider;

    public ParticipantRepository(ParticipantProvider participantProvider) {
        this.participantProvider = participantProvider;
    }

    public ParticipantInfo getParticipantByUserId(UUID userId) {
        Participant participant = participantProvider.getParticipantByUserId(userId);
        if (participant == null) {
            throw new ParticipantNotFoundException(userId);
        }
        return ParticipantInfo.builder()
                .userId(participant.getUserId())
                .username(participant.getUsername())
                .challengeId(participant.getChallengeId())
                .locationId(participant.getLocationId())
                .points(participant.getPoints())
                .build();
    }

    public ParticipantInfo createParticipant(UUID userId, String username, Integer locationId) {
        Participant toBeCreatedParticipant = new Participant();
        toBeCreatedParticipant.setUserId(userId);
        toBeCreatedParticipant.setUsername(username);
        toBeCreatedParticipant.setLocationId(locationId);
        Participant createdParticipantInfo = participantProvider.createParticipant(toBeCreatedParticipant);

        return ParticipantInfo.builder()
                .userId(createdParticipantInfo.getUserId())
                .username(createdParticipantInfo.getUsername())
                .challengeId(createdParticipantInfo.getChallengeId())
                .locationId(createdParticipantInfo.getLocationId())
                .points(createdParticipantInfo.getPoints())
                .build();
    }

    public Boolean participateInChallenge(UUID userId, UUID challengeId) {
        boolean isParticipantEnteredChallenge = participantProvider.participateInChallenge(userId, challengeId);
        if (!isParticipantEnteredChallenge) {
            throw new ParticipantNotFoundException(userId, "Not found the Participant to enter the challenge!");
        }
        return true;
    }

    public ParticipantInfo claimReward(UUID userId, Integer reward) {
        boolean isRewardClaimed = participantProvider.claimReward(userId, reward);
        if (!isRewardClaimed) {
            throw new ParticipantNotFoundException(userId, "Not found the user to claim the reward!");
        }
        return getParticipantByUserId(userId);
    }
}
