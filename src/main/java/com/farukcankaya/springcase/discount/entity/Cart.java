package com.farukcankaya.springcase.discount.entity;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Cart {
  @NotEmpty List<Product> items;

  public Cart() {}

  public Cart(List<Product> items) {
    this.items = items;
  }

  public List<Product> getItems() {
    return items;
  }

  public void setItems(List<Product> items) {
    this.items = items;
  }
}
