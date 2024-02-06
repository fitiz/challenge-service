package com.fitiz.challenge.controller;

import com.fitiz.challenge.dto.ParticipantCreateRequest;
import com.fitiz.challenge.model.ParticipantInfo;
import com.fitiz.challenge.service.ParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

  private final ParticipantService participantService;

  public ParticipantController(ParticipantService participantService) {
    this.participantService = participantService;
  }

  @Operation(summary = "Creates a participant user in the application")
  @PostMapping("")
  public ParticipantInfo createParticipant(@RequestBody ParticipantCreateRequest request) {
    return participantService.createParticipant(request.getUserId(), request.getUsername(), request.getLocationId());
  }
}
