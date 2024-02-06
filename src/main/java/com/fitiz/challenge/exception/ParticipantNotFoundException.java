package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ParticipantNotFoundException extends AbstractServiceException {

  private String message = "Not found the user!";

  private final UUID userId;

  public ParticipantNotFoundException(UUID userId) {
    this.userId = userId;
  }

  public ParticipantNotFoundException(UUID userId, String message) {
    this.userId = userId;
    this.message = message;
  }

  @Override
  public String getServiceExceptionMessage() {
    return this.message + " User Id: " + userId;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

}
