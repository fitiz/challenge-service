package com.fitiz.challenge.service;

import com.fitiz.challenge.exception.*;
import com.fitiz.challenge.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ChallengeManager {

    private final ChallengeService challengeService;

    private final ParticipantService participantService;

    private final RewardService rewardService;

    public ChallengeManager(ChallengeService challengeService, ParticipantService participantService,
                            RewardService rewardService) {
        this.challengeService = challengeService;
        this.participantService = participantService;
        this.rewardService = rewardService;
    }

    public ChallengeInfo createChallenge(String name, Integer locationId) {
        Optional<ChallengeInfo> challengeInfoOptional = challengeService.getChallengeByLocationId(locationId);
        if (challengeInfoOptional.isPresent() && challengeInfoOptional.get().isActive()) {
            throw new ChallengeAlreadyExistsException();
        }

        return challengeService.createChallenge(name, locationId);
    }

    public List<LeaderboardInfo> participateInChallenge(UUID userId) {
        ParticipantInfo participant = participantService.getParticipantByUserId(userId);
        Optional<ChallengeInfo> challengeInfoOptional = challengeService.getChallengeByLocationId(participant.getLocationId());
        if (challengeInfoOptional.isEmpty()) {
            throw new ChallengeNotFoundException();
        }

        ChallengeInfo challengeInfo = challengeInfoOptional.get();
        if (!challengeInfo.isActive()) {
            throw new ChallengeNotActiveException();
        }

        if (participant.isInAnotherChallenge(challengeInfo.getId())) {
            throw new ChallengeRewardNotClaimedException();
        }

        if (!challengeService.addParticipantToChallengeSet(challengeInfo.getId(), participant.getUsername())) {
            throw new ChallengeHasSameParticipantException();
        }

        String leaderboardId = challengeService.getChallengeLeaderboardId(challengeInfo.getId());

        try {
            participantService.participateInChallenge(participant.getUserId(), challengeInfo.getId());
            challengeService.addToLeaderboardRedis(leaderboardId, participant.getUsername());
            challengeService.addToLeaderboardPg(challengeInfo.getId(), participant.getUserId());
            // todo use cache to get user details from username toLeaderboardInfo(leaderboardDataList);
            List<LeaderboardData> leaderboard = challengeService.getLeaderboard(leaderboardId);
            return toLeaderboardInfo(leaderboard);
        } catch (Exception exception) {
            challengeService.saveBackGroupId(challengeInfo.getId(), leaderboardId);
            challengeService.removeParticipantFromChallengeSet(challengeInfo.getId(), participant.getUsername());
            throw exception;
        }
    }

    public ParticipantProgress claimReward(UUID userId) {
        ParticipantInfo participant = participantService.getParticipantByUserId(userId);
        Optional<ChallengeInfo> challengeInfoOptional = challengeService.getChallengeByLocationId(participant.getLocationId());
        if (challengeInfoOptional.isEmpty()) {
            throw new ChallengeNotFoundException();
        }
        ChallengeInfo challengeInfo = challengeInfoOptional.get();

        if (participant.getChallengeId() == null) {
            throw new ParticipantChallengeIdNotFoundException();
        }

        if (challengeInfo.getId().equals(participant.getChallengeId()) && challengeInfo.isActive()) {
            throw new ChallengeNotAvailableException();
        }

        if (!challengeService.removeParticipantFromChallengeSet(challengeInfo.getId(), participant.getUsername())) {
            throw new ParticipantChallengeIdNotFoundException();
        }

        try {
            String leaderboardId = challengeService.getChallengeLeaderboardId(challengeInfo.getId());

            Integer rank = challengeService.getRank(leaderboardId, participant.getUsername());
            Integer rewardedPoints = rewardService.getReward(rank);
            return participantService.claimReward(userId, rewardedPoints);
        } catch (Exception exception) {
            challengeService.addParticipantToChallengeSet(challengeInfo.getId(), participant.getUsername());
            throw exception;
        }
    }

    public List<LeaderboardInfo> getLeaderboard(UUID userId) {
        ParticipantInfo participant = participantService.getParticipantByUserId(userId);
        if (participant.getChallengeId() == null) {
            throw new ParticipantChallengeIdNotFoundException();
        }
        String leaderboardId = challengeService.getChallengeLeaderboardId(participant.getChallengeId());

        List<LeaderboardData> leaderboardDataList = challengeService.getLeaderboard(leaderboardId);
        return toLeaderboardInfo(leaderboardDataList);
    }

    public Integer getRank(UUID userId) {
        ParticipantInfo participant = participantService.getParticipantByUserId(userId);
        if (participant.getChallengeId() == null) {
            throw new ParticipantChallengeIdNotFoundException();
        }

        String leaderboardId = challengeService.getChallengeLeaderboardId(participant.getChallengeId());
        return challengeService.getRank(leaderboardId, participant.getUsername());
    }

    public List<LeaderboardInfo> getLeaderboardByLocationId(Integer locationId) {
        ChallengeInfo challengeInfo = challengeService.getChallengeByLocationId(locationId)
                .orElseThrow(ChallengeNotFoundException::new);
        String leaderboardId = challengeService.getChallengeLeaderboardId(challengeInfo.getId());
        List<LeaderboardData> leaderboardDataList = challengeService.getLeaderboard(leaderboardId);
        return toLeaderboardInfo(leaderboardDataList);
    }

    private static List<LeaderboardInfo> toLeaderboardInfo(List<LeaderboardData> leaderboard) {
        return leaderboard.stream().map(leaderboardData ->
                new LeaderboardInfo(leaderboardData.username(), leaderboardData.steps())).toList();
    }


}
