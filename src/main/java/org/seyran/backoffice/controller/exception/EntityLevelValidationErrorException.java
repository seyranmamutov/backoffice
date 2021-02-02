package org.seyran.backoffice.controller.exception;

import lombok.Getter;

@Getter
public class EntityLevelValidationErrorException extends RuntimeException {

  private EntityErrorMsgKey msgKey;
  private Object[] args;

  public EntityLevelValidationErrorException(EntityErrorMsgKey msgKey, Object... args) {
    this.msgKey = msgKey;
    this.args = args;
  }
}
