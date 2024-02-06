package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

public class ChallengeNotFoundException extends AbstractServiceException {

  private String message = "Challenge is not found!";

  public ChallengeNotFoundException() {
  }

  public ChallengeNotFoundException(String message) {
    this.message = message;
  }

  @Override
  public String getServiceExceptionMessage() {
    return this.message;
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

}
