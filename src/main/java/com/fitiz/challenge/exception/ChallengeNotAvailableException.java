package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

public class ChallengeNotAvailableException extends AbstractServiceException {

  private String message = "Challenge is not available for claiming to reward!";

  public ChallengeNotAvailableException() {
  }

  public ChallengeNotAvailableException(String message) {
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
