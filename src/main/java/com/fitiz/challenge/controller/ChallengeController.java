package com.fitiz.challenge.controller;

import com.fitiz.challenge.dto.ChallengeCreateRequest;
import com.fitiz.challenge.model.ChallengeInfo;
import com.fitiz.challenge.model.LeaderboardInfo;
import com.fitiz.challenge.model.ParticipantProgress;
import com.fitiz.challenge.service.ChallengeManager;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeManager challengeManager;

    public ChallengeController(ChallengeManager challengeManager) {
        this.challengeManager = challengeManager;
    }

    @Operation(summary = "Creates a challenge in the application")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ChallengeInfo createChallenge(@RequestBody ChallengeCreateRequest request) {
        return challengeManager.createChallenge(request.getName(), request.getLocationId());
    }

    @Operation(summary = "Participate in the challenge")
    @PostMapping("/participants/{userId}")
    public List<LeaderboardInfo> participateInChallenge(@PathVariable UUID userId) {
        return challengeManager.participateInChallenge(userId);
    }

    @Operation(summary = "Claims the reward of a specific challenge")
    @PostMapping("/participants/{userId}/rewards")
    public ParticipantProgress claimChallengeReward(@PathVariable UUID userId) {
        return challengeManager.claimReward(userId);
    }

    @Operation(summary = "Gets the rank of a specific player")
    @GetMapping("/participants/{userId}/ranks")
    public Integer getRank(@PathVariable UUID userId) {
        return challengeManager.getRank(userId);
    }

    @Operation(summary = "Gets the leaderboard of the challenge for a specific player")
    @GetMapping("participants/{userId}/leaderboards")
    public List<LeaderboardInfo> getLeaderboard(@PathVariable UUID userId) {
        return challengeManager.getLeaderboard(userId);
    }

    @Operation(summary = "Gets the leaderboard of the challenge for a specific location")
    @GetMapping("locations/{locationId}/leaderboards")
    public List<LeaderboardInfo> getLeaderboardByLocationId(@PathVariable Integer locationId) {
        return challengeManager.getLeaderboardByLocationId(locationId);
    }
}
