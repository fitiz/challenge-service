package com.fitiz.challenge.provider;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ChallengeGroupProvider {

    private StatefulRedisConnection<String, String> connection;

    public ChallengeGroupProvider(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        RedisURI redisURI = RedisURI.builder()
                .withHost(redisStandaloneConfiguration.getHostName())
                .withPort(redisStandaloneConfiguration.getPort())
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);
        this.connection = redisClient.connect();
    }

    public Boolean addToChallengeSet(UUID challengeId, String username) {
        String key = getRedisChallengeParticipantsKey(challengeId);
        return connection.sync().sadd(key, username) == 1;
    }

    public Boolean removeFromChallengeSet(UUID challengeId, String username) {
        String key = getRedisChallengeParticipantsKey(challengeId);
        return connection.sync().srem(key, username) == 1;
    }

    public String getUnusedGroupId(UUID challengeId) {
        String key = getRedisUnusedGroupKey(challengeId);
        return connection.sync().lpop(key);
    }

    public Boolean saveUnusedGroupId(UUID challengeId, String groupId) {
        String key = getRedisUnusedGroupKey(challengeId);
        return connection.sync().lpush(key, groupId) == 1;
    }

    // key: `challenge-<challengeId>-participants` to look up participants that are currently in the challenge!
    private String getRedisChallengeParticipantsKey(UUID challengeId) {
        return "challenge-" + challengeId + "-participants";
    }

    // key: `unused-challenge-<challengeId>` key for looking up unused list of keys for the challenge!
    private String getRedisUnusedGroupKey(UUID challengeId) {
        return "unused-" + "challenge-" + challengeId;
    }
}
