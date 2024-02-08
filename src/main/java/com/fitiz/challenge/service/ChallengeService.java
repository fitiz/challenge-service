package com.fitiz.challenge.service;

import com.fitiz.challenge.model.ChallengeInfo;
import com.fitiz.challenge.model.LeaderboardData;
import com.fitiz.challenge.repository.ChallengeGroupRepository;
import com.fitiz.challenge.repository.ChallengeRepository;
import com.fitiz.challenge.repository.LeaderboardPgRepository;
import com.fitiz.challenge.repository.LeaderboardRedisRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    private final ChallengeGroupRepository challengeGroupRepository;

    private final LeaderboardRedisRepository leaderboardRedisRepository;

    private final LeaderboardPgRepository leaderboardPgProvider;

    public ChallengeService(ChallengeRepository challengeRepository, ChallengeGroupRepository challengeGroupRepository,
                            LeaderboardRedisRepository leaderboardRedisRepository, LeaderboardPgRepository leaderboardPgProvider) {
        this.challengeRepository = challengeRepository;
        this.challengeGroupRepository = challengeGroupRepository;
        this.leaderboardRedisRepository = leaderboardRedisRepository;
        this.leaderboardPgProvider = leaderboardPgProvider;
    }

    public ChallengeInfo createChallenge(String name, Integer locationId) {
        ZoneId zoneId = ZoneId.of("UTC");
        LocalDate currentDate = LocalDate.now(zoneId);
        LocalTime startTime = LocalTime.MIN;
        ZonedDateTime startDateTime = ZonedDateTime.of(currentDate, startTime, zoneId);
        ZonedDateTime endDateTime = ZonedDateTime.of(currentDate.plusDays(0), LocalTime.MAX, zoneId);

        ChallengeInfo challengeInfo = ChallengeInfo.builder()
                .name(name)
                .startDate(startDateTime)
                .finishDate(endDateTime)
                .locationId(locationId)
                .build();
        challengeRepository.createChallenge(challengeInfo);
        return challengeInfo;
    }

    public Optional<ChallengeInfo> getChallengeById(UUID challengeId) {
        return challengeRepository.getChallengeById(challengeId);
    }

    public Optional<ChallengeInfo> getChallengeByLocationId(Integer locationId) {
        return challengeRepository.getChallengeByLocationId(locationId);
    }

    public Boolean addToLeaderboardRedis(String groupId, String username) {
        return leaderboardRedisRepository.addToLeaderboardRedis(groupId, username);
    }

    public void addToLeaderboardPg(UUID challengeId, UUID userId) {
        leaderboardPgProvider.addToLeaderboardPg(challengeId, userId);
    }

    public List<LeaderboardData> getLeaderboard(String groupId) {
        return leaderboardRedisRepository.getLeaderboard(groupId);
    }

    public Integer getRank(String groupId, String username) {
        return leaderboardRedisRepository.getRank(groupId, username) + 1;
    }

    public Boolean addParticipantToChallengeSet(UUID challengeId, String username) {
        return challengeGroupRepository.addParticipantToChallengeSet(challengeId, username);
    }

    public Boolean removeParticipantFromChallengeSet(UUID challengeId, String username) {
        return challengeGroupRepository.removeParticipantFromChallengeSet(challengeId, username);
    }

    public Integer getChallengeSetSize(UUID challengeId) {
        return challengeGroupRepository.getChallengeSetSize(challengeId);
    }


    public String getChallengeLeaderboardId(UUID challengeId) {
        return challengeGroupRepository.getChallengeId(challengeId);
    }

    public Boolean saveBackGroupId(UUID challengeId, String groupId) {
        return challengeGroupRepository.saveBackGroupId(challengeId, groupId);
    }
}
