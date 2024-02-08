package com.fitiz.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ParticipantCreateRequest {

    @NotBlank(message = "userId cannot be blank")
    private UUID userId;

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotBlank(message = "locationId cannot be blank")
    private Integer locationId;

    public ParticipantCreateRequest() {
    }

    public ParticipantCreateRequest(UUID userId, String username, Integer locationId) {
        this.userId = userId;
        this.username = username;
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "ParticipantCreateRequest{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", locationId=" + locationId +
                '}';
    }
}
