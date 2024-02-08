package com.fitiz.challenge.controller;

import com.fitiz.challenge.dto.ChallengeCreateRequest;
import com.fitiz.challenge.dto.ChallengeParticipationRequest;
import com.fitiz.challenge.dto.ClaimRewardRequest;
import com.fitiz.challenge.model.ChallengeInfo;
import com.fitiz.challenge.model.LeaderboardInfo;
import com.fitiz.challenge.model.ParticipantProgress;
import com.fitiz.challenge.service.ChallengeManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;

import java.util.Optional;
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
    @PostMapping("/{challengeId}/participants/{userId}")
    public LeaderboardInfo participateInChallenge(@RequestBody ChallengeParticipationRequest request) {
        return challengeManager.participateInChallenge(request.getChallengeId(), request.getUserId());
    }

    @Operation(summary = "Claims the reward of a specific challenge")
    @PostMapping("/{challengeId}/participants/{userId}/rewards")
    public ParticipantProgress claimChallengeReward(@RequestBody ClaimRewardRequest request) {
        return challengeManager.claimReward(request.getChallengeId(), request.getUserId());
    }

    @Operation(summary = "Gets the rank of a specific player")
    @GetMapping("/participants/{userId}/ranks")
    public Integer getRank(@PathVariable UUID userId) {
        return challengeManager.getRank(userId);
    }

    @Operation(summary = "Gets the leaderboard of the challenge for a specific player")
    @GetMapping("/participants/{userId}/leaderboards")
    public LeaderboardInfo getLeaderboard(@PathVariable UUID userId) {
        return challengeManager.getLeaderboard(userId);
    }

    @Operation(summary = "Gets the challenge info of a specific location")
    @GetMapping("/locations/{locationId}")
    public ResponseEntity<ChallengeInfo> getChallengeByLocationId(@PathVariable Integer locationId) {
        Optional<ChallengeInfo> challengeInfoOptional = challengeManager.getChallengeByLocationId(locationId);
        return challengeInfoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Gets the leaderboard of the challenge for a specific location")
    @GetMapping("/locations/{locationId}/leaderboards")
    public LeaderboardInfo getLeaderboardByLocationId(@PathVariable Integer locationId) {
        return challengeManager.getLeaderboardByLocationId(locationId);
    }
}
