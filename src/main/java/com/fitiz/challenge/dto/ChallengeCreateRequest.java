package com.fitiz.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChallengeCreateRequest {

  @NotBlank(message = "name cannot be blank")
  private String name;

  @NotBlank(message = "locationId cannot be blank")
  private Integer locationId;

  public ChallengeCreateRequest() {
  }

  public ChallengeCreateRequest(String name, Integer locationId) {
    this.name = name;
    this.locationId = locationId;
  }

  @Override
  public String toString() {
    return "ChallengeCreateRequest{" +
            "name='" + name + '\'' +
            ", locationId=" + locationId +
            '}';
  }
}
