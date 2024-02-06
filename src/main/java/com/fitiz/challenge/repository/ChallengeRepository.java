package com.fitiz.challenge.repository;

import com.fitiz.challenge.model.ChallengeInfo;
import com.fitiz.challenge.provider.ChallengeProvider;
import com.fitiz.challenge.tables.pojos.Challenge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.fitiz.challenge.utils.Constants.DEFAULT_ZONE_ID;

@Service
public class ChallengeRepository {
    private final ChallengeProvider challengeProvider;

    public ChallengeRepository(ChallengeProvider challengeProvider) {
        this.challengeProvider = challengeProvider;
    }

    public Optional<ChallengeInfo> getChallengeById(UUID challengeId) {
        Optional<Challenge> challengeOptional = challengeProvider.getChallengeById(challengeId);
        return challengeOptional.flatMap(ChallengeRepository::buildChallengeInfo);
    }

    public Optional<ChallengeInfo> getChallengeByLocationId(Integer locationId) {
        Optional<Challenge> challengeOptional = challengeProvider.getChallengeByLocationId(locationId);
        return challengeOptional.flatMap(ChallengeRepository::buildChallengeInfo);
    }

    private static Optional<ChallengeInfo> buildChallengeInfo(Challenge challenge) {
        ChallengeInfo challengeInfo = ChallengeInfo.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .startDate(challenge.getStartDate().atZone(DEFAULT_ZONE_ID))
                .finishDate(challenge.getFinishDate().atZone(DEFAULT_ZONE_ID))
                .locationId(challenge.getLocationId())
                .build();
        return Optional.of(challengeInfo);
    }

    public void createChallenge(ChallengeInfo challengeInfo) {
        Challenge challenge = new Challenge();
        challenge.setName(challengeInfo.getName());
        challenge.setStartDate(challengeInfo.getStartDate().toLocalDateTime());
        challenge.setFinishDate(challengeInfo.getFinishDate().toLocalDateTime());
        challenge.setLocationId(challengeInfo.getLocationId());
        challengeProvider.createChallenge(challenge);
    }
}
