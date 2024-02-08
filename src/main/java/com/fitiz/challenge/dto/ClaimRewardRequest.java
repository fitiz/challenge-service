package com.fitiz.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ClaimRewardRequest {

    @NotBlank(message = "challengeId cannot be blank")
    private UUID challengeId;

    @NotBlank(message = "userId cannot be blank")
    private UUID userId;

    public ClaimRewardRequest() {
    }

    public ClaimRewardRequest(UUID challengeId, UUID userId) {
        this.challengeId = challengeId;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ClaimRewardRequest{" +
                "challengeId=" + challengeId +
                ", userId=" + userId +
                '}';
    }
}
