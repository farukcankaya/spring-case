package com.farukcankaya.springcase.campaign.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@DiscriminatorColumn(name = "type")
@JsonTypeInfo(visible = true, use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CategoryCampaign.class, name = "category"),
  @JsonSubTypes.Type(value = ProductCampaign.class, name = "product")
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Campaign {
  @Id @GeneratedValue private Long id;

  @NotBlank private String name;

  @NotNull
  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  @NotNull @Positive private BigDecimal discountValue;

  @Nullable @Positive private BigDecimal maximumDiscountPrice;

  public Campaign() {}

  public Campaign(
      Long id,
      @NotNull String name,
      @NotNull DiscountType discountType,
      @NotNull BigDecimal discountValue,
      BigDecimal maximumDiscountPrice) {
    this.id = id;
    this.name = name;
    this.discountType = discountType;
    this.discountValue = discountValue;
    this.maximumDiscountPrice = maximumDiscountPrice;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DiscountType getDiscountType() {
    return discountType;
  }

  public void setDiscountType(DiscountType discountType) {
    this.discountType = discountType;
  }

  public BigDecimal getDiscountValue() {
    return discountValue;
  }

  public void setDiscountValue(BigDecimal discountValue) {
    this.discountValue = discountValue;
  }

  public BigDecimal getMaximumDiscountPrice() {
    return maximumDiscountPrice;
  }

  public void setMaximumDiscountPrice(BigDecimal maximumDiscountPrice) {
    this.maximumDiscountPrice = maximumDiscountPrice;
  }
}
