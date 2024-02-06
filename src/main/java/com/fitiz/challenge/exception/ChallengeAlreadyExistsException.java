package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

public class ChallengeAlreadyExistsException extends AbstractServiceException {

  private String message = "Challenge is already exist and active!";

  public ChallengeAlreadyExistsException() {
  }

  public ChallengeAlreadyExistsException(String message) {
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
