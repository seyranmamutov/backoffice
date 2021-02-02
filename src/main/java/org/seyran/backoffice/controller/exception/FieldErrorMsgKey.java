package org.seyran.backoffice.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldErrorMsgKey {
  USER_CHANGE_PWD_WRONG_CURRENT,
  USER_CHANGE_PWD_WRONG_CONFIRM,
  USER_DOMAIN_DISPLAY_INCORRECT_VALUE,
  USER_DOMAN_PRICE_REQUIRED,
  USER_DOMAN_MIN_OFFER_PRICE_REQUIRED,
  USER_DOMAN_NAME_INVALID,
  USER_DOMAN_NAME_DUPLICATE_REQUEST,
  USER_DOMAN_NAME_DUPLICATE
}
