package com.farukcankaya.springcase.discount;

import com.farukcankaya.springcase.TestUtils;
import com.farukcankaya.springcase.campaign.CampaignRepository;
import com.farukcankaya.springcase.campaign.entity.Campaign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceTest {
  @Mock CampaignRepository campaignRepositoryMock;

  @InjectMocks DiscountService discountService;

  @Test
  public void givenCampaignList_whenGetAllCampaigns_thenReturnCampaignList() {
    List<Campaign> campaignList = TestUtils.getCaseCampaigns();

    when(campaignRepositoryMock.findAll()).thenReturn(campaignList);

    assertThat(
            TestUtils.extractCalculatedDiscounts(
                discountService.calculateDiscounts(TestUtils.getCaseProducts())))
        .isEqualTo(TestUtils.getCaseCalculatedDiscount());
  }
}
