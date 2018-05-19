package com.farukcankaya.springcase.discount;

import com.farukcankaya.springcase.common.ListResponse;
import com.farukcankaya.springcase.discount.entity.Cart;
import com.farukcankaya.springcase.discount.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/calculateDiscounts")
public class DiscountRestController {

  DiscountRestController() {}

  private DiscountService discountService;

  @Autowired
  public DiscountRestController(DiscountService discountService) {
    this.discountService = discountService;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public ListResponse<Product> calculateDiscounts(@Valid @RequestBody Cart cart) {

    return new ListResponse<>(discountService.calculateDiscounts(cart.getItems()));
  }
}
