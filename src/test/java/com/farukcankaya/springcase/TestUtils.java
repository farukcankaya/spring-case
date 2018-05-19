package com.farukcankaya.springcase;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestUtils {
  static List<Campaign> campaignList =
      Arrays.asList(
          new ProductCampaign(
              1L,
              "Product Campaign 1",
              DiscountType.PRICE,
              new BigDecimal(1),
              null,
              1L,
              "Product #1"),
          new CategoryCampaign(
              2L,
              "Category Campaign 1",
              DiscountType.PRICE,
              new BigDecimal(2),
              null,
              2L,
              "Category #1"),
          new ProductCampaign(
              3L,
              "Product Campaign 2",
              DiscountType.PRICE,
              new BigDecimal(2),
              null,
              2L,
              "Product #2"),
          new CategoryCampaign(
              4L,
              "Category Campaign 2",
              DiscountType.PRICE,
              new BigDecimal(1),
              null,
              1L,
              "Category #2"));

  public static List<Campaign> getRandomCampaigns(int size) {
    Collections.shuffle(campaignList);
    return campaignList.subList(0, size);
  }
}
