package com.fitiz.challenge.service;

import org.springframework.stereotype.Component;

@Component
public class RewardService {

  private static final Integer RANK_1_REWARD = 10000;

  private static final Integer RANK_2_REWARD = 5000;

  private static final Integer RANK_3_REWARD = 3000;

  private static final Integer RANK_4_10_REWARD = 1000;

  private static final Integer RANK_REST_REWARD = 100;

  public Integer getReward(Integer rank) {
    if (rank == 1) {
      return RANK_1_REWARD;
    } else if (rank == 2) {
      return RANK_2_REWARD;
    } else if (rank == 3) {
      return RANK_3_REWARD;
    } else if (rank >= 4 && rank <= 10) {
      return RANK_4_10_REWARD;
    } else {
      return RANK_REST_REWARD;
    }
  }
}
