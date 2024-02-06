package com.fitiz.challenge.exception;

import com.fitiz.challenge.exception.exceptionhandler.AbstractServiceException;
import org.springframework.http.HttpStatus;

public class ChallengeRewardNotClaimedException extends AbstractServiceException {

  private String message = "The user must claim latest reward to join a new challenge!";

  public ChallengeRewardNotClaimedException() {
  }

  public ChallengeRewardNotClaimedException(String message) {
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
