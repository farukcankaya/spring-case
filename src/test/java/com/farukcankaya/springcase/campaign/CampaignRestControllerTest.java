package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.farukcankaya.springcase.common.NotFoundException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CampaignRestController.class, secure = false)
public class CampaignRestControllerTest {
  private static ObjectMapper mapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;

  @MockBean private CampaignService mockCampaignService;

  @Test
  public void givenEmptyList_whenCampaignsInvoked_thenReturnEmptyJsonArray() throws Exception {
    // G
    List<Campaign> campaignList = new ArrayList<>(0);
    String responseContent = "{\"items\":[]}";

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

  @Test
  public void givenEmptyCampaign_whenCampaignsByIdInvoked_thenReturn404WithInformation()
      throws Exception {
    // G
    long campaignId = 1;
    NotFoundException notFoundException = new NotFoundException(Campaign.class);
    String responseContent = "{\"messages\":[\"Campaign is not found\"]}";

    // W
    when(mockCampaignService.getCampaignById(campaignId)).thenThrow(notFoundException);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/campaigns/" + campaignId).accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaign_whenCampaignsByIdInvoked_thenReturnCampaignJson() throws Exception {
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
    String responseContent =
        "{\"type\":\"product\",\"id\":1,\"name\":\"Product Campaign\",\"discountType\":\"RATE\",\"discountValue\":10,\"maximumDiscountPrice\":80,\"productId\":1,\"productName\":\"Product #1\"}";

    // W
    when(mockCampaignService.getCampaignById(productCampaign.getId())).thenReturn(productCampaign);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/campaigns/" + productCampaign.getId())
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaignJson_whenAddCampaignInvoked_thenReturnCampaignJson() throws Exception {
    // G
    Campaign savedCampaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent = mapper.writeValueAsString(savedCampaign);

    // W
    Mockito.when(mockCampaignService.addCampaign(Mockito.any())).thenReturn(savedCampaign);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaignWithNullCampaignType_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload = "{ \"name\": \"Campaign\" }";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void givenCampaignWithWrongCampaignType_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload = "{ \"type\": \"X\" }";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void givenCampaignWithNullDiscountValue_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent = "{\"messages\":[\"discountValue must not be null but null is sent\"]}";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaignWithNullName_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent = "{\"messages\":[\"name must not be blank but null is sent\"]}";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaignWithNullDiscountType_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent = "{\"messages\":[\"discountType must not be null but null is sent\"]}";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaignWithNullProductId_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productName\": \"Product #1\" }";
    String responseContent = "{\"messages\":[\"productId must not be null but null is sent\"]}";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenCampaignWithNullProductName_whenAddCampaignInvoked_thenReturnBadRequestError()
      throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productId\": 1 }";
    String responseContent = "{\"messages\":[\"productName must not be empty but null is sent\"]}";

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/campaigns")
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }
}
