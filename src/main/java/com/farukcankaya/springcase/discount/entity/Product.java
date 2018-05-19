package com.farukcankaya.springcase.discount.entity;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Product {
  @Positive @NotNull private Long productId;
  @Positive @NotNull private Long categoryId;
  @Positive @NotNull private BigDecimal price;
  @Positive @Nullable private BigDecimal discountedPrice;

  public Product() {}

  public Product(@NotNull Long productId, @NotNull Long categoryId, @NotNull BigDecimal price) {
    this.productId = productId;
    this.categoryId = categoryId;
    this.price = price;
  }

  public Product(
      @NotNull Long productId,
      @NotNull Long categoryId,
      @NotNull BigDecimal price,
      BigDecimal discountedPrice) {
    this.productId = productId;
    this.categoryId = categoryId;
    this.price = price;
    this.discountedPrice = discountedPrice;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getDiscountedPrice() {
    return discountedPrice;
  }

  public void setDiscountedPrice(BigDecimal discountedPrice) {
    this.discountedPrice = discountedPrice;
  }
}
