package com.farukcankaya.springcase.campaign.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("product")
public class ProductCampaign extends Campaign {
  @NotNull private Long productId;

  @NotEmpty private String productName;

  public ProductCampaign() {}

  public ProductCampaign(
      Long id,
      @NotNull String name,
      @NotNull DiscountType discountType,
      @NotNull BigDecimal discountValue,
      BigDecimal maximumDiscountPrice,
      @NotNull Long productId,
      @NotNull String productName) {
    super(id, name, discountType, discountValue, maximumDiscountPrice);
    this.productId = productId;
    this.productName = productName;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }
}
