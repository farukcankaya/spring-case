package com.farukcankaya.springcase.common;

public class NotFoundException extends RuntimeException {

  public NotFoundException(Class clazz) {
    super(clazz.getSimpleName() + " is not found");
  }
}
