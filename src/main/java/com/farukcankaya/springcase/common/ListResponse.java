package com.farukcankaya.springcase.common;

import java.util.List;

public class ListResponse<T> {
  List<T> items;

  public ListResponse(List<T> items) {
    this.items = items;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }
}
