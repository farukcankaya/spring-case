package com.farukcankaya.springcase.discount;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.discount.entity.Cart;
import com.farukcankaya.springcase.discount.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DiscountRestController.class, secure = false)
public class DiscountRestControllerTest {
  private static ObjectMapper mapper = new ObjectMapper();

  @Autowired private MockMvc mockMvc;

  @MockBean private DiscountService mockDiscountService;

  @Test
  public void givenCartJson_whenCalculateDiscountsInvoked_thenReturnDiscountedPrices()
      throws Exception {
    // G
    List<BigDecimal> discountedPrices = TestUtils.getCaseCalculatedDiscount();
    List<Product> products = TestUtils.getCaseProducts();
    String requestPayload =
        "{ \"items\": [ { \"productId\":10, \"categoryId\":200, \"price\":100.99 }, { \"productId\":5, \"categoryId\":100, \"price\":200 }, { \"productId\":20, \"categoryId\":100, \"price\":500 } ] }";

    IntStream.range(0, discountedPrices.size())
        .mapToObj(
            i -> {
              products.get(i).setDiscountedPrice(discountedPrices.get(i));
              return products.get(i);
            })
        .collect(Collectors.toList());
    String responseContent = mapper.writeValueAsString(new Cart(products));

    // W
    Mockito.when(mockDiscountService.calculateDiscounts(Mockito.any())).thenReturn(products);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/v1/calculateDiscounts")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    // assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }
}
