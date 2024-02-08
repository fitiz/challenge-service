package com.fitiz.challenge.repository;

import com.fitiz.challenge.model.LeaderboardData;
import com.fitiz.challenge.provider.LeaderboardRedisProvider;
import io.lettuce.core.ScoredValue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardRedisRepository {

  private final LeaderboardRedisProvider leaderboardRedisProvider;

  public LeaderboardRedisRepository(LeaderboardRedisProvider leaderboardRedisProvider) {
    this.leaderboardRedisProvider = leaderboardRedisProvider;
  }

  public Boolean addToLeaderboardRedis(String leaderboardId, String username) {
    return leaderboardRedisProvider.addToLeaderboard(leaderboardId, username).intValue() != 0;
  }

  public List<LeaderboardData> getLeaderboard(String leaderboardId) {
    List<ScoredValue<String>> leaderboard = leaderboardRedisProvider.getLeaderboard(leaderboardId);
    return leaderboard.stream()
                        .map(data -> new LeaderboardData(
                                data.getValue(),
                                (int) data.getScore()))
                        .collect(Collectors.toList());
  }

  public Integer getRank(String leaderboardId, String username) {
    return leaderboardRedisProvider.getRank(leaderboardId, username).intValue();
  }
}
