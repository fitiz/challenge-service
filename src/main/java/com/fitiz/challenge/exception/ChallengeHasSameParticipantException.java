package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

public class ChallengeHasSameParticipantException extends AbstractServiceException {

  private String message = "The participant already is in this challenge!";

  public ChallengeHasSameParticipantException() {
  }

  public ChallengeHasSameParticipantException(String message) {
    this.message = message;
  }

  @Override
  public String getServiceExceptionMessage() {
    return this.message;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.CONFLICT;
  }

}
