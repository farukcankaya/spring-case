package com.farukcankaya.springcase.common;

public class NotFoundException extends RuntimeException {

  public NotFoundException(Class clazz, String... searchParamsMap) {
    super(clazz.getSimpleName() + " #" + searchParamsMap + " not found");
  }
}
