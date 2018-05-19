package com.farukcankaya.springcase.campaign;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import com.farukcankaya.springcase.campaign.entity.CategoryCampaign;
import com.farukcankaya.springcase.campaign.entity.DiscountType;
import com.farukcankaya.springcase.campaign.entity.ProductCampaign;
import com.farukcankaya.springcase.common.MissingParameterException;
import com.farukcankaya.springcase.common.NotFoundException;
import com.farukcankaya.springcase.common.WrongValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CampaignServiceTest {
  @Mock CampaignRepository campaignRepositoryMock;

  @InjectMocks CampaignService campaignService;

  @Test
  public void givenCampaignList_whenGetAllCampaigns_thenReturnCampaignList() {
    List<Campaign> campaignList = TestUtils.getRandomCampaigns(2);

    when(campaignRepositoryMock.findAll()).thenReturn(campaignList);

    assertThat(campaignService.getAllCampaigns()).isEqualTo(campaignList);
  }

  // #getCampaignById
  @Test(expected = NotFoundException.class)
  public void givenNull_whenGetCampaignById_thenThowNotFoundException() {
    Campaign campaign = TestUtils.getRandomCampaigns(1).get(0);

    when(campaignRepositoryMock.findById(campaign.getId())).thenReturn(Optional.empty());

    campaignService.getCampaignById(campaign.getId());
  }

  @Test
  public void givenCampaign_whenGetCampaignById_thenReturnCampaign() {
    Campaign campaign = TestUtils.getRandomCampaigns(1).get(0);

    when(campaignRepositoryMock.findById(campaign.getId())).thenReturn(Optional.of(campaign));

    assertThat(campaignService.getCampaignById(campaign.getId())).isEqualTo(campaign);
  }

  // #addCampaign
  @Test
  public void givenCampaign_whenAddCampaign_thenReturnCampaign() {
    Campaign campaign =
        new ProductCampaign(
            null,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    Campaign savedCampaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    when(campaignRepositoryMock.save(campaign)).thenReturn(savedCampaign);

    assertThat(campaignService.addCampaign(campaign)).isEqualTo(savedCampaign);
  }

  @Test(expected = MissingParameterException.class)
  public void
      givenRateCampaignWithMissingMaximumDiscountPrice_whenAddCampaign_thenThrowMissingParameterException() {
    Campaign campaign =
        new ProductCampaign(
            null,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            null,
            1L,
            "Product #1");

    campaignService.addCampaign(campaign);
  }

  @Test
  public void givenRateCampaignWithMaximumDiscountPrice_whenAddCampaign_thenReturnCampaign() {
    Campaign campaign =
        new ProductCampaign(
            null,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    Campaign savedCampaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    when(campaignRepositoryMock.save(campaign)).thenReturn(savedCampaign);

    assertThat(campaignService.addCampaign(campaign)).isEqualTo(savedCampaign);
  }

  @Test(expected = WrongValueException.class)
  public void givenRateCampaignWrongDiscountValue_whenAddCampaign_thenThrowWrongValueException() {
    Campaign campaign =
        new ProductCampaign(
            null,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(100.01),
            new BigDecimal(80),
            1L,
            "Product #1");

    campaignService.addCampaign(campaign);
  }

  // #updateCampaign
  @Test
  public void givenCampaign_whenUpdateCampaign_thenReturnCampaign() {
    Campaign campaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    when(campaignRepositoryMock.findById(campaign.getId())).thenReturn(Optional.of(campaign));
    when(campaignRepositoryMock.save(campaign)).thenReturn(campaign);

    assertThat(campaignService.updateCampaign(campaign.getId(), campaign)).isEqualTo(campaign);
  }

  @Test(expected = NotFoundException.class)
  public void giveNonExistingCampaign_whenUpdateCampaign_thenRThrowNotFoundException() {
    Campaign campaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");

    campaignService.updateCampaign(5L, campaign);
  }

  @Test(expected = MissingParameterException.class)
  public void
      givenRateCampaignWithMissingMaximumDiscountPrice_whenUpdateCampaign_thenThrowMissingParameterException() {
    // G
    Campaign campaign =
        new ProductCampaign(
            null,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            null,
            1L,
            "Product #1");

    // T
    campaignService.updateCampaign(campaign.getId(), campaign);
  }

  @Test
  public void givenRateCampaignWithMaximumDiscountPrice_whenUpdateCampaign_thenReturnCampaign() {
    // G
    Campaign campaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");

    // W
    when(campaignRepositoryMock.findById(campaign.getId())).thenReturn(Optional.of(campaign));
    when(campaignRepositoryMock.save(campaign)).thenReturn(campaign);

    // T
    assertThat(campaignService.updateCampaign(campaign.getId(), campaign)).isEqualTo(campaign);
  }

  @Test(expected = WrongValueException.class)
  public void
      givenRateCampaignWrongDiscountValue_whenUpdateCampaign_thenThrowWrongValueException() {
    // G
    Campaign campaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(100.01),
            new BigDecimal(80),
            1L,
            "Product #1");

    campaignService.updateCampaign(campaign.getId(), campaign);
  }

  @Test(expected = CampaignTypeMismatchException.class)
  public void
      givenCampaignWithDifferentType_whenUpdateCampaign_thenThrowCampaignTypeMismatchException() {
    // G
    Campaign productCampaign =
        new ProductCampaign(
            1L,
            "Product Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Product #1");
    Campaign categoryCampaign =
        new CategoryCampaign(
            1L,
            "Category Campaign",
            DiscountType.RATE,
            new BigDecimal(10),
            new BigDecimal(80),
            1L,
            "Category #1");

    // W
    when(campaignRepositoryMock.findById(categoryCampaign.getId())).thenReturn(Optional.of(categoryCampaign));

    campaignService.updateCampaign(productCampaign.getId(), productCampaign);
  }
}
