package com.farukcankaya.springcase.campaign;

public class CampaignTypeMismatchException extends RuntimeException {

  public CampaignTypeMismatchException() {
    super("Campaign type cannot be changed");
  }
}
