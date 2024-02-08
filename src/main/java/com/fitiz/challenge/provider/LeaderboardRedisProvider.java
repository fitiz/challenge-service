package com.fitiz.challenge.provider;

import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.lettuce.core.ZAddArgs.Builder.nx;

@Repository
public class LeaderboardRedisProvider {

  private StatefulRedisConnection<String, String> connection;

  public LeaderboardRedisProvider(RedisStandaloneConfiguration redisStandaloneConfiguration) {
    RedisURI redisURI = RedisURI.builder()
                                .withHost(redisStandaloneConfiguration.getHostName())
                                .withPort(redisStandaloneConfiguration.getPort()).build();
    RedisClient redisClient = RedisClient.create(redisURI);
    this.connection = redisClient.connect();
  }

  /**
   * Adds given user id to leaderboard specified by leaderboard key.
   * User's initial score is 0. If user is already in table, it doesn't reset the score.
   */
  public Long addToLeaderboard(String key, String username) {
    return connection.sync().zadd(key, nx(), 0, username);
  }

  /**
   * Increments the user's score by steps on the board.
   * !!Important!! if user is not exist in the board, it just adds a new user to board with score 0 and increments it.
   */
  public Double updateSteps(String key, Integer steps, String username) {
    return connection.sync().zincrby(key, steps, username);
  }

  public List<ScoredValue<String>> getLeaderboard(String key) {
    return connection.sync().zrevrangeWithScores(key, 0, 9);
  }

  public Long getRank(String key, String username) {
    return connection.sync().zrevrank(key, username);
  }
}
