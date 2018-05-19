package com.farukcankaya.springcase.common;

public class MissingParameterException extends RuntimeException {

  public MissingParameterException(String parameter) {
    super(parameter + " must not be empty for campaign which has discount in RATE type.");
  }
}
