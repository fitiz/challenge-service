package com.fitiz.challenge.service;

import com.fitiz.challenge.model.ParticipantInfo;
import com.fitiz.challenge.model.ParticipantProgress;
import com.fitiz.challenge.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ParticipantService {

  private final ParticipantRepository participantRepository;

  public ParticipantService(ParticipantRepository participantRepository) {
    this.participantRepository = participantRepository;
  }

  public ParticipantInfo getParticipantByUserId(UUID userId) {
    return participantRepository.getParticipantByUserId(userId);
  }

  public ParticipantInfo createParticipant(UUID userId, String username, Integer locationId) {
    return participantRepository.createParticipant(userId, username, locationId);
  }

  public Boolean participateInChallenge(UUID userId, UUID challengeId) {
    return participantRepository.participateInChallenge(userId, challengeId);
  }

  public ParticipantProgress claimReward(UUID userId, Integer reward) {
    ParticipantInfo userInfo = participantRepository.claimReward(userId, reward);
    return userInfo.toParticipantProgress();
  }
}
