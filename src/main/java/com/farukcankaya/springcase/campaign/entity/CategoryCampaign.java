package com.farukcankaya.springcase.campaign.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("category")
public class CategoryCampaign extends Campaign {
  @NotNull private Long categoryId;

  @NotEmpty private String categoryName;

  public CategoryCampaign() {}

  public CategoryCampaign(
      Long id,
      @NotNull String name,
      @NotNull DiscountType discountType,
      @NotNull BigDecimal discountValue,
      BigDecimal maximumDiscountPrice,
      @NotNull Long categoryId,
      @NotNull String categoryName) {
    super(id, name, discountType, discountValue, maximumDiscountPrice);
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
