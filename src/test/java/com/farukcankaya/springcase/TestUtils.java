package com.farukcankaya.springcase;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.farukcankaya.springcase.discount.entity.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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

  public static List<Campaign> getCaseCampaigns() {
    return Arrays.asList(
        new ProductCampaign(
            1L,
            "Ucuz Ayfon",
            DiscountType.RATE,
            new BigDecimal(5),
            new BigDecimal(100),
            5L,
            "Ayfon Y 64GB"),
        new ProductCampaign(
            2L,
            "Mavi Tişört İndirimde",
            DiscountType.PRICE,
            new BigDecimal(50),
            null,
            10L,
            "Mavi Tişört"),
        new CategoryCampaign(
            3L,
            "Kazaklar Daha Ucuz",
            DiscountType.PRICE,
            new BigDecimal(10),
            new BigDecimal(30),
            100L,
            "Kazak"),
        new CategoryCampaign(
            4L,
            "İndirimli Gömlekler",
            DiscountType.PRICE,
            new BigDecimal(20),
            null,
            200L,
            "Gömlek"));
  }

  public static List<Product> getCaseProducts() {
    return Arrays.asList(
        new Product(10L, 200L, new BigDecimal(100.99)),
        new Product(5L, 100L, new BigDecimal(200)),
        new Product(20L, 100L, new BigDecimal(500)));
  }

  public static BigDecimal[] extractCalculatedDiscounts(List<Product> products) {
    return products
        .stream()
        .map(product -> product.getDiscountedPrice())
        .toArray(BigDecimal[]::new);
  }

  public static BigDecimal[] getCaseCalculatedDiscount() {
    BigDecimal[] discountedPrices = {
      new BigDecimal(30.99), new BigDecimal(190), new BigDecimal(470)
    };
    return discountedPrices;
  }
}
