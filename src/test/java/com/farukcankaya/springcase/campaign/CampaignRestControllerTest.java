package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.farukcankaya.springcase.common.Constants;
import com.farukcankaya.springcase.common.MissingParameterException;
import com.farukcankaya.springcase.common.NotFoundException;
import com.farukcankaya.springcase.common.WrongValueException;
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

  // GET /campaigns
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

  // GET /campaigns/:id
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

  // POST campaigns
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

  @Test
  public void
      givenRateCampaignWithMissingMaximumDiscountValue_whenAddCampaignInvoked_thenReturnBadRequestError()
          throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent =
        "{\"messages\":[\"maximumDiscountPrice must not be empty for campaign which has discount in RATE type.\"]}";
    MissingParameterException exception = new MissingParameterException("maximumDiscountPrice");

    // W
    when(mockCampaignService.addCampaign(Mockito.any())).thenThrow(exception);

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
  public void
      givenRateCampaignWithWrongDiscountValue_whenAddCampaignInvoked_thenReturnBadRequestError()
          throws Exception {
    // G
    String requestPayload =
        "{ \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 100.01, \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent =
        "{\"messages\":[\"discountValue must be less than or equal to "
            + Constants.MAXIMUM_DISCOUNT_VALUE
            + "\"]}";
    WrongValueException exception =
        new WrongValueException("discountValue", Constants.MAXIMUM_DISCOUNT_VALUE);

    // W
    when(mockCampaignService.addCampaign(Mockito.any())).thenThrow(exception);

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

  // PATCH campaigns
  @Test
  public void givenCampaignJson_whenUpdateCampaignInvoked_thenReturnCampaignJson()
      throws Exception {
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
        "{ \"id\": 1, \"type\": \"product\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent = mapper.writeValueAsString(savedCampaign);

    // W
    Mockito.when(mockCampaignService.updateCampaign(Mockito.any(), Mockito.any()))
        .thenReturn(savedCampaign);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/campaigns/" + savedCampaign.getId().longValue())
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void
      givenCampaignWithNullCampaignType_whenUpdateCampaignInvoked_thenReturnBadRequestError()
          throws Exception {
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
        "{ \"id\": 1, \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"productId\": 1, \"productName\": \"Product #1\" }";
    String responseContent = mapper.writeValueAsString(savedCampaign);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/campaigns/" + 1)
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
  }

  @Test
  public void
      givenCampaignWithDifferentCampaignType_whenUpdateCampaignInvoked_thenReturnBadRequestError()
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
    CampaignTypeMismatchException exception = new CampaignTypeMismatchException();
    String requestPayload =
        "{ \"id\": 1, \"type\": \"category\", \"name\": \"Product Campaign\", \"discountType\":\"RATE\", \"discountValue\": 10, \"maximumDiscountPrice\": 80, \"categoryId\": 1, \"categoryName\": \"Category #1\" }";
    String responseContent = "{\"messages\":[\"Campaign type cannot be changed\"]}";

    // W
    when(mockCampaignService.updateCampaign(Mockito.anyLong(), Mockito.any())).thenThrow(exception);

    // T
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/campaigns/" + productCampaign.getId().longValue())
            .content(requestPayload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  // destory
  @Test
  public void givenNonExistingCampaignId_whenDestroyInvoked_thenReturn404WithInformation()
          throws Exception {
    // G
    long campaignId = 1;
    NotFoundException notFoundException = new NotFoundException(Campaign.class);
    String responseContent = "{\"messages\":[\"Campaign is not found\"]}";

    // W
    when(mockCampaignService.deleteCampaign(campaignId)).thenThrow(notFoundException);

    // T
    RequestBuilder requestBuilder =
            MockMvcRequestBuilders.delete("/campaigns/" + campaignId).accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

  @Test
  public void givenExistingCampaignId_whenDestroyInvoked_thenReturn204NoContent() throws Exception {
    // G
    long campaignId = 1;
    String responseContent = "";

    // W
    when(mockCampaignService.deleteCampaign(campaignId)).thenReturn(true);

    // T
    RequestBuilder requestBuilder =
            MockMvcRequestBuilders.delete("/campaigns/" + campaignId)
                    .accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    assertEquals(responseContent, response.getContentAsString());
  }

}
