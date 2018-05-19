package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CampaignRestController.class, secure = false)
public class CampaignRestControllerTest {
  private static ObjectMapper mapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;

  @MockBean private CampaignService mockCampaignService;

  @Test
  public void givenCampaignList_whenCampaignsInvoked_thenReturnCampaignListJsonArray()
      throws Exception {
    // G
    ProductCampaign productCampaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    CategoryCampaign categoryCampaign =
        new CategoryCampaign(
            2L,
            "Category Campaign",
            DiscountType.PRICE,
            new BigDecimal(50),
            null,
            1L,
            "Category #1");
    List<Campaign> campaignList = Arrays.asList(productCampaign, categoryCampaign);
    String responseContent =
        "{\"items\":[{\"id\":1,\"name\":\"Product Campaign\",\"discountType\":\"RATE\",\"discountValue\":10,\"maximumDiscountPrice\":80,\"productId\":1,\"productName\":\"Product #1\"},{\"id\":2,\"name\":\"Category Campaign\",\"discountType\":\"PRICE\",\"discountValue\":50,\"maximumDiscountPrice\":null,\"categoryId\":1,\"categoryName\":\"Category #1\"}]}";

    // W
    when(mockCampaignService.getAllCampaigns()).thenReturn(campaignList);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/campaigns").accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }
}
