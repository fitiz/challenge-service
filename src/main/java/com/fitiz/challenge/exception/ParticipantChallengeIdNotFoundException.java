package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

public class ParticipantChallengeIdNotFoundException extends AbstractServiceException {

  private String message = "The user is not in a challenge!";

  public ParticipantChallengeIdNotFoundException() {
  }

  public ParticipantChallengeIdNotFoundException(String message) {
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
