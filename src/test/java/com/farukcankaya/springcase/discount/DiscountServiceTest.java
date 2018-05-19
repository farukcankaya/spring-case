package com.farukcankaya.springcase.discount;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.campaign.CampaignRepository;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.farukcankaya.springcase.discount.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceTest {
  @Mock CampaignRepository campaignRepositoryMock;

  @InjectMocks DiscountService discountService;

  @Test
  public void givenProductList_whenApplyDiscountToProduct_thenReturnDiscountedPriceAtLeast0() {
    List<Campaign> campaignList =
        Arrays.asList(
            new ProductCampaign(
                1L,
                "Product",
                DiscountType.RATE,
                new BigDecimal(100),
                new BigDecimal(100),
                5L,
                "Product"),
            new CategoryCampaign(
                2L, "Category", DiscountType.PRICE, new BigDecimal(10), null, 5L, "Category"));

    campaignList
        .stream()
        .filter(campaign -> campaign instanceof CategoryCampaign)
        .forEach(
            campaign ->
                when(campaignRepositoryMock.findByCategoryId(
                        ((CategoryCampaign) campaign).getCategoryId()))
                    .thenReturn(Optional.of(campaign)));

    campaignList
        .stream()
        .filter(campaign -> campaign instanceof ProductCampaign)
        .forEach(
            campaign ->
                when(campaignRepositoryMock.findByProductId(
                        ((ProductCampaign) campaign).getProductId()))
                    .thenReturn(Optional.of(campaign)));

    List<Product> productList = Arrays.asList(new Product(5L, 5L, BigDecimal.ZERO));
    List<BigDecimal> actual =
        TestUtils.extractCalculatedDiscounts(discountService.calculateDiscounts(productList));
    List<BigDecimal> expected = Arrays.asList(BigDecimal.ZERO);
    double precision = 5;

    assertArrayEquals(scaleBigDecimals(expected), scaleBigDecimals(actual), precision);
  }

  @Test
  public void givenProductList_whenCalculateDiscounts_thenReturnDiscountedProducts() {
    List<Campaign> campaignList = TestUtils.getCaseCampaigns();

    campaignList
        .stream()
        .filter(campaign -> campaign instanceof CategoryCampaign)
        .forEach(
            campaign ->
                when(campaignRepositoryMock.findByCategoryId(
                        ((CategoryCampaign) campaign).getCategoryId()))
                    .thenReturn(Optional.of(campaign)));

    campaignList
        .stream()
        .filter(campaign -> campaign instanceof ProductCampaign)
        .forEach(
            campaign ->
                when(campaignRepositoryMock.findByProductId(
                        ((ProductCampaign) campaign).getProductId()))
                    .thenReturn(Optional.of(campaign)));

    List<BigDecimal> actual =
        TestUtils.extractCalculatedDiscounts(
            discountService.calculateDiscounts(TestUtils.getCaseProducts()));
    List<BigDecimal> expected = TestUtils.getCaseCalculatedDiscount();
    double precision = 5;

    assertArrayEquals(scaleBigDecimals(expected), scaleBigDecimals(actual), precision);
  }

  // TODO: There should better way!
  private double[] scaleBigDecimals(List<BigDecimal> list) {
    return list.stream().mapToDouble(BigDecimal::doubleValue).toArray();
  }
}
