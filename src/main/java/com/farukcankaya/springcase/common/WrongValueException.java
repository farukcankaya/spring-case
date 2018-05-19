package com.farukcankaya.springcase.common;

import java.math.BigDecimal;

public class WrongValueException extends RuntimeException {

  public WrongValueException(String parameter, BigDecimal value) {
    super(parameter + " must be less than or equal to " + value.toEngineeringString());
  }
}
